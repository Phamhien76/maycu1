package ra.project5.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.WishListRequest;
import ra.project5.model.dto.response.ProductResponse;
import ra.project5.model.dto.response.WishListResponse;
import ra.project5.model.dto.response.WishProductResponse;
import ra.project5.model.entity.Product;
import ra.project5.model.entity.User;
import ra.project5.model.entity.WishList;
import ra.project5.repository.ProductRepository;
import ra.project5.repository.UserRepository;
import ra.project5.repository.WishListRepository;
import ra.project5.service.WishListService;

import java.util.List;
import java.util.Optional;

@Service
public class WishListServiceImp implements WishListService {
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public WishListResponse save(WishListRequest wishListRequest) throws CustomException {
        Product product = productRepository.findById(wishListRequest.getProductId())
                .orElseThrow(() -> new CustomException("ProductId not found"));
        User user = userRepository.findById(wishListRequest.getUserId())
                .orElseThrow(()-> new CustomException("UserId not found"));
         productRepository.save(product);
        WishList wishList = WishList.builder()
                .product(product)
                .user(user).build();

        return modelMapper.map(wishListRepository.save(wishList), WishListResponse.class);
    }

    @Override
    public List<WishListResponse> findAll() {
        List<WishList> listWishList = wishListRepository.findAll();
        List<WishListResponse> listWishListResponse = listWishList.stream()
                .map(wishList -> modelMapper.map(wishList, WishListResponse.class)).toList();
        return listWishListResponse;
    }

    @Override
    public boolean delete(Long wistListId) throws CustomException {
     WishList wishList = wishListRepository.findById(wistListId)
             .orElseThrow(()-> new CustomException("WishListId not found"));
     Product product = productRepository.findById(wishList.getProduct().getProductId())
             .orElseThrow(()-> new CustomException("ProductId not found"));
     wishListRepository.delete(wishList);
     return true;
    }

    @Override
    public List<WishProductResponse> featuredProduct() {
        List<WishProductResponse> listProduct= wishListRepository.featuredProducts();
        return listProduct;
    }

}
