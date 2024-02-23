package ra.project5.serviceImp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.OrderPatchRequest;
import ra.project5.model.dto.request.OrderRequest;
import ra.project5.model.dto.response.*;
import ra.project5.model.entity.*;
import ra.project5.repository.*;
import ra.project5.service.OrderService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductRepository productRepository;
    @Value( "${ra.received.at.time}")
    private  Long receivedAtTime;

    @Autowired
    private EntityManager entityManager;

    // USER

    @Override
    public OrderResponse save(OrderRequest orderRequest) throws CustomException {
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new CustomException("UserId not found"));
        Address address = addressRepository.findById(orderRequest.getAddressId())
                .orElseThrow(() -> new CustomException("AddressId not found"));

        //Chuyển đổi từ lít Id shopping cart sang List đối tượng shopping Cart
        List<ShoppingCart> listShopping = new ArrayList<>();
        for (Integer item: orderRequest.getShoppingCartId()){
            listShopping.add(shoppingCartRepository.findById(item)
                    .orElseThrow(() -> new CustomException("shoppingcartId not found")));

        }
        //Tính tổng tiền
        double totalPrice = 0.0;
        for (ShoppingCart shoppingCart: listShopping){
            totalPrice +=shoppingCart.getProduct().getUnitPrice()*shoppingCart.getOrderQuantity();
        }
      Order order = Order.builder()
              .user(user)
              .serialNumber(orderRequest.getSerialNumber())
              .note(orderRequest.getNote())
              .receiveName(address.getReceiveName())
              .receiveAddress(address.getFullAddress())
              .receivePhone(address.getPhone())
              .createdAt(new Date())
              .receivedAt(new Date(new Date().getTime()+receivedAtTime))
              .status(EOrder.WAITING)
              .orderAt(new Date())
              .totalPrice(totalPrice)
              .build();
        Order newOrder = orderRepository.save(order);
        //Tạo orderDetails
        for (ShoppingCart shoppingCart: listShopping){
            OderDetail oderDetail = OderDetail.builder()
                    .order(newOrder)
                    .product(shoppingCart.getProduct())
                    .productName(shoppingCart.getProduct().getProductName())
                    .unitPrice(shoppingCart.getProduct().getUnitPrice())
                    .orderQuantity(shoppingCart.getOrderQuantity()).build();
            orderDetailRepository.save(oderDetail);
        }


        //Sử lý lượng hàng còn lại sau khi order
        for (ShoppingCart shoppingCart: listShopping){
            if (shoppingCart.getProduct().getStockQuantity()>=shoppingCart.getOrderQuantity()){
                shoppingCart.setOrderQuantity(shoppingCart.getOrderQuantity());
            }else {
                throw new CustomException("Hết hàng");
            }
            shoppingCart.getProduct().setStockQuantity(shoppingCart.getProduct().getStockQuantity() - shoppingCart.getOrderQuantity());
            productRepository.save(shoppingCart.getProduct());
        }
        // Xóa shopping cart
        for (Integer item: orderRequest.getShoppingCartId()) {
            shoppingCartRepository.deleteById(item);
        }



        return OrderResponse.builder()
                .serialNumber(newOrder.getSerialNumber())
                .userId(newOrder.getUser().getUserId())
                .orderAt(newOrder.getOrderAt())
                .totalPrice(newOrder.getTotalPrice())
                .status(newOrder.getStatus())
                .note(newOrder.getNote())
                .receiveName(newOrder.getReceiveName())
                .receiveAddress(newOrder.getReceiveAddress())
                .receivePhone(newOrder.getReceivePhone())
                .build();

    }

    @Override
    public OrderResponse cancelOrder(Long orderId, Long userId) throws CustomException {
        Order orderCancel = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException("OrderId not found"));

        if (orderCancel.getUser().getUserId().equals(userId)){
            if (orderCancel.getStatus().equals(EOrder.WAITING)){
                orderCancel.setStatus(EOrder.CANCEL);
                Order newOrder = orderRepository.save(orderCancel);

                List<OderDetail> listOderDetails = orderCancel.getListOderDetail();
                for (OderDetail oderDetail: listOderDetails){
                    Product product = oderDetail.getProduct();
                    product.setStockQuantity(product.getStockQuantity()+oderDetail.getOrderQuantity());
                    productRepository.save(product);
                }
                return modelMapper.map(newOrder, OrderResponse.class);
            }else {
                throw new CustomException("không có quyền hủy Order khi không phải là trạng thái WAITING");
            }
        }else {
            throw new CustomException("không có quyền hủy Order user khác");
        }
    }

    @Override
    public List<OrderResponse> findAllOrderByUserId(Long userId) throws CustomException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("UserId not found"));
        List<Order> listOrder = orderRepository.findAllByUserUserId(userId);
        List<OrderResponse> listOrderResponse = listOrder.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class)).toList();
        return listOrderResponse;
    }

    @Override
    public List<OrderResponse> findAllOrderHistoryByStatus(Long userId, String status) throws CustomException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException("UserId not found"));
           EOrder eOrderFindAll = EOrder.valueOf(status);
        List<Order> listOrder = orderRepository.findAllByUserUserIdAndStatus(userId, eOrderFindAll);
        List<OrderResponse> listOrderResponse = listOrder.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class)).toList();
        return listOrderResponse;
    }

    @Override
    public List<BestSellerProductResponse> findBestSellerProducts() {
        List<BestSellerProductResponse> listProduct= orderDetailRepository.findBestSellerProducts();
        return listProduct;
    }

    @Override
    public Double salesTotalPrice(java.sql.Date from, java.sql.Date to) {
        Double totalSales=0.0;
        List<Order> listOrder = orderRepository.salesTotalPrice(from,to,EOrder.SUCCESS);
        for (Order order: listOrder ){
            totalSales += order.getTotalPrice();
        }
        return totalSales;
    }


    @Override
    public List<ProductBestSellerProducts> getBestSellerProductsOfMonth() {
        List<ProductBestSellerProducts> listProduct = orderDetailRepository.findBestSellerProductsOfMonth();
        return listProduct;


//        TypedQuery<ProductBestSellerProducts> query = entityManager.createQuery(
//                "SELECT new ra.project5.model.dto.response.ProductBestSellerProducts(od.product.productId,od.product.productName,SUM (od.orderQuantity))" +
//                        "FROM OderDetail od " +
//                        "JOIN od.order o " +
//                        "WHERE YEAR (o.createdAt) = YEAR (CURRENT_DATE ) " +
//                        "AND MONTH (o.createdAt) = MONTH (CURRENT_DATE )" +
//                        "GROUP BY  od.product " +
//                        "ORDER BY SUM (od.orderQuantity) DESC ", ProductBestSellerProducts.class
//
//        );


    }

    @Override
    public List<CategoriesSales> getCategoriesSale() {
        TypedQuery<CategoriesSales> query = entityManager.createQuery(
                "SELECT new ra.project5.model.dto.response.CategoriesSales(c.catalogName, SUM (od.unitPrice * od.orderQuantity))" +
                        "FROM OderDetail od " +
                        "JOIN od.product p " +
                        "JOIN p.catalog c " +
                        "GROUP BY c.catalogName", CategoriesSales.class);
        return query.getResultList();
    }


    //ADMIN


    @Override
    public List<OrderResponse> findAllOrder() {
        List<Order> listOrder = orderRepository.findAll();
        List<OrderResponse> listOrderResponse = listOrder.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class)).toList();
        return listOrderResponse;
    }

    @Override
    public List<OrderResponse> findAllOrderByStatus(String status) {
        EOrder eOrderFindAll = EOrder.valueOf(status);
        List<Order> listOrder = orderRepository.findAllByStatusIs(eOrderFindAll);
        List<OrderResponse> listOrderResponse = listOrder.stream()
                .map(order -> modelMapper.map(order, OrderResponse.class)).toList();
        return listOrderResponse;
    }

    @Override
    public List<OderDetailResponse> findAllOrderDetail(Long orderId) {

        List<OderDetail> listOderDetail = orderDetailRepository.findAllByOrderOrderId(orderId);

        List<OderDetailResponse> listOderDetailResponse = listOderDetail.stream()
                .map(oderDetail -> OderDetailResponse.builder()
                        .orderDetailId(oderDetail.getOrderDetailId())
                        .orderId(oderDetail.getOrder().getOrderId())
                        .productId(oderDetail.getProduct().getProductId())
                        .productName(oderDetail.getProductName())
                        .unitPrice(oderDetail.getUnitPrice())
                        .orderQuantity(oderDetail.getOrderQuantity()).build()).toList();
        return listOderDetailResponse;
    }

    @Override
    public OrderResponse updateOrderStatus(OrderPatchRequest orderPatchRequest) throws CustomException {
       Order orderStatusUpdate = orderRepository.findById(orderPatchRequest.getOrderId())
               .orElseThrow(() -> new CustomException("OrderId not found"));
       EOrder status = EOrder.valueOf(orderPatchRequest.getStatus());
       orderStatusUpdate.setStatus(status);

       Order orderNew = orderRepository.save(orderStatusUpdate);
        return modelMapper.map(orderNew, OrderResponse.class);
    }






}
