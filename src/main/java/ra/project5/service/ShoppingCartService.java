package ra.project5.service;

import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.ShoppingCartPatchRequest;
import ra.project5.model.dto.request.ShoppingCartRequest;
import ra.project5.model.dto.response.ShoppingCartResponse;

import java.util.List;

public interface ShoppingCartService {
ShoppingCartResponse save(ShoppingCartRequest shoppingCartRequest) throws CustomException;
ShoppingCartResponse updateOrderQuantity(ShoppingCartPatchRequest shoppingCartPatchRequest) throws CustomException;
boolean deleteOrderProduct(Long userId, Integer shoppingCartId) throws CustomException;
boolean deleteAllProduct(Long userId);
List<ShoppingCartResponse> findAllByUserId(Long userId);



}
