package ra.project5.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.ShoppingCartPatchRequest;
import ra.project5.model.dto.request.ShoppingCartRequest;
import ra.project5.model.dto.response.ShoppingCartResponse;
import ra.project5.model.entity.Product;
import ra.project5.model.entity.ShoppingCart;
import ra.project5.model.entity.User;
import ra.project5.repository.ProductRepository;
import ra.project5.repository.ShoppingCartRepository;
import ra.project5.repository.UserRepository;
import ra.project5.service.ShoppingCartService;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImp implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public ShoppingCartResponse save(ShoppingCartRequest shoppingCartRequest) throws CustomException {


        if (shoppingCartRequest.getOrderQuantity()<=0){

            throw new CustomException("OrderQuantity must be greater than 0.");
        }
        ShoppingCart checkshoppingCart = shoppingCartRepository.findByProductProductIdAndUserUserId(
                shoppingCartRequest.getProductId(), shoppingCartRequest.getUserId()) ;
        // nếu tồn tại thì công số lượng
        if (checkshoppingCart != null){
            if (checkshoppingCart.getProduct().getStockQuantity()>=checkshoppingCart.getOrderQuantity()){
                checkshoppingCart.setOrderQuantity(checkshoppingCart.getOrderQuantity()+1);
                // thay đổi rồi lưu vào database
                return modelMapper.map(shoppingCartRepository.save(checkshoppingCart),ShoppingCartResponse.class);
            }else {
                throw new CustomException("Hết hàng");
            }
        } else {
            // nếu không thì khởi tạo đối tượng shoppingCart mới xong lưu vào
            Product product = productRepository.findByProductIdAndProductStatus(shoppingCartRequest.getProductId(),true)
                    .orElseThrow(() -> new CustomException("Product not found"));
            User user = userRepository.findById(shoppingCartRequest.getUserId())
                    .orElseThrow(() -> new CustomException("UserId not found"));
            if (product.getStockQuantity()>=shoppingCartRequest.getOrderQuantity()){
                ShoppingCart shoppingCart = ShoppingCart.builder()
                        .product(product)
                        .user(user)
                        .orderQuantity(shoppingCartRequest.getOrderQuantity()).build();
                return modelMapper.map(shoppingCartRepository.save(shoppingCart),ShoppingCartResponse.class);
            }else {
                throw new CustomException("Hết hàng");
            }

        }

    }

    @Override
    public ShoppingCartResponse updateOrderQuantity(ShoppingCartPatchRequest shoppingCartPatchRequest) throws CustomException {
        ShoppingCart shoppingCartUpdate = shoppingCartRepository.findById(shoppingCartPatchRequest.getShoppingCartId())
                .orElseThrow(() -> new CustomException("ShoppingCartId not found"));
        if (shoppingCartUpdate.getUser().getUserId().equals(shoppingCartPatchRequest.getUserId())){
            if (shoppingCartUpdate.getProduct().getStockQuantity()>=shoppingCartPatchRequest.getOrderQuantity()){
                shoppingCartUpdate.setOrderQuantity(shoppingCartPatchRequest.getOrderQuantity());
            }else {
                throw new CustomException("Hết hàng");
            }
        }else {
            throw new CustomException("UserId not found");
        }

        return modelMapper.map(shoppingCartRepository.save(shoppingCartUpdate), ShoppingCartResponse.class);
    }

    @Override
    public boolean deleteOrderProduct( Long userId,Integer shoppingCartId) throws CustomException {

       ShoppingCart shoppingCart = shoppingCartRepository.findById(shoppingCartId)
               .orElseThrow(() -> new CustomException("SoppingCarId not fount"));
       if (shoppingCart.getUser().getUserId().equals(userId)) {
           shoppingCartRepository.delete(shoppingCart);
           return true;
       }
        throw new CustomException("UserId not found");
    }

    @Override
    public boolean deleteAllProduct(Long userId) {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByUserUserId(userId);
        if (!shoppingCarts.isEmpty()){
            shoppingCartRepository.deleteAll(shoppingCarts);
            return true;
        }
              return false;

    }
    @Override
    public List<ShoppingCartResponse> findAllByUserId(Long userId) {

        List<ShoppingCart> listShoppingCart = shoppingCartRepository.findAllByUserUserId(userId);
        List<ShoppingCartResponse> listShoppingCartResponse = listShoppingCart.stream()
                .map(shoppingCart -> modelMapper.map(shoppingCart, ShoppingCartResponse.class)).toList();
        return listShoppingCartResponse;
    }
}
