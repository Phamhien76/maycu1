package ra.project5.service;

import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.OrderPatchRequest;
import ra.project5.model.dto.request.OrderRequest;
import ra.project5.model.dto.response.*;
import ra.project5.model.entity.EOrder;

import java.sql.Date;
import java.util.List;

public interface OrderService {
    OrderResponse save (OrderRequest orderRequest) throws CustomException;
    List<OrderResponse> findAllOrder();
    List<OrderResponse> findAllOrderByStatus(String status);
    List<OderDetailResponse> findAllOrderDetail(Long orderId);
    OrderResponse updateOrderStatus (OrderPatchRequest orderPatchRequest) throws CustomException;
    OrderResponse cancelOrder(Long orderId, Long userId) throws CustomException;
    List<OrderResponse> findAllOrderByUserId(Long userId) throws CustomException;
    List<OrderResponse> findAllOrderHistoryByStatus(Long userId, String status) throws CustomException;
    List<BestSellerProductResponse> findBestSellerProducts();
    Double salesTotalPrice(java.sql.Date from, Date to);
    List<ProductBestSellerProducts> getBestSellerProductsOfMonth();
    List<CategoriesSales> getCategoriesSale();




}
