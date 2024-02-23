package ra.project5.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.OrderRequest;
import ra.project5.model.dto.request.ShoppingCartPatchRequest;
import ra.project5.model.dto.request.ShoppingCartRequest;
import ra.project5.model.dto.response.Message;
import ra.project5.model.dto.response.ShoppingCartResponse;
import ra.project5.service.OrderService;
import ra.project5.service.ShoppingCartService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shopping-cart")
public class UserShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<ShoppingCartResponse> save (@RequestBody ShoppingCartRequest shoppingCartRequest) throws CustomException {
        return new ResponseEntity<>(shoppingCartService.save(shoppingCartRequest), HttpStatus.CREATED);

    }
    @PatchMapping
    public ResponseEntity<ShoppingCartResponse> updateOrderQuantity(@RequestBody ShoppingCartPatchRequest shoppingCartPatchRequest) throws CustomException {
        return new ResponseEntity<>(shoppingCartService.updateOrderQuantity(shoppingCartPatchRequest),HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/delete/{shoppingCartId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long userId,@PathVariable Integer shoppingCartId) throws CustomException {
        if (shoppingCartService.deleteOrderProduct(userId,shoppingCartId)){
            return new ResponseEntity<>("Delete successful",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Id not found",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<?> deleteAllProduct(@PathVariable Long userId){
        if (shoppingCartService.deleteAllProduct(userId)){
            return new ResponseEntity<>("Delete successful",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Id not found",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/checkOut")
    public ResponseEntity<?> orderCheckOut(@RequestBody OrderRequest orderRequest) throws CustomException {
        return new ResponseEntity<>(orderService.save(orderRequest),HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ShoppingCartResponse>> findAllByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(shoppingCartService.findAllByUserId(userId),HttpStatus.OK);
    }





}
