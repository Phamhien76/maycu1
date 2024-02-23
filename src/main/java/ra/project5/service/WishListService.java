package ra.project5.service;

import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.WishListRequest;
import ra.project5.model.dto.response.ProductResponse;
import ra.project5.model.dto.response.WishListResponse;
import ra.project5.model.dto.response.WishProductResponse;
import ra.project5.model.entity.WishList;

import java.util.List;

public interface WishListService {
    WishListResponse save (WishListRequest wishListRequest) throws CustomException;
    List<WishListResponse> findAll();
    boolean delete(Long wistListId) throws CustomException;
    List<WishProductResponse> featuredProduct();


}
