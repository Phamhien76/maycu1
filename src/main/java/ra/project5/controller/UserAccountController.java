package ra.project5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.AddressPostRequest;
import ra.project5.model.dto.request.PasswordPutRequest;
import ra.project5.model.dto.request.UserPutRequest;
import ra.project5.model.dto.response.AddressResponse;
import ra.project5.model.dto.response.OrderResponse;
import ra.project5.model.dto.response.UserResponse;
import ra.project5.service.AddressService;
import ra.project5.service.OrderService;
import ra.project5.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
public class UserAccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private OrderService orderService;

    // USER
    @PutMapping
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserPutRequest userPutRequest) throws CustomException {
        return new ResponseEntity<>(userService.update(userPutRequest), HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> findByUserId(@PathVariable Long userId) throws CustomException {
        return new ResponseEntity<>(userService.findById(userId),HttpStatus.OK);

    }

    @PutMapping("/changePassword")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordPutRequest passwordPutRequest) throws CustomException {
        userService.updatePassword(passwordPutRequest);
        return new ResponseEntity<>("Thay đổi mật khẩu thành công", HttpStatus.OK);
    }

    // ADDRESS


    @PostMapping("/address")
    public ResponseEntity<AddressResponse> saveAddress(@RequestBody AddressPostRequest addressPostRequest) throws CustomException {
        return new ResponseEntity<>(addressService.saveAddressByUserId(addressPostRequest), HttpStatus.CREATED);
    }

    @GetMapping("/{userId}/address")
    public ResponseEntity<List<AddressResponse>> findAllAddressByUserId(@PathVariable Long userId){
        return new ResponseEntity<>(addressService.findAllSaveAddressByUserId(userId),HttpStatus.OK);
    }

    @GetMapping("/{userId}/address/{addressId}")
    public ResponseEntity<AddressResponse> findAddressById(@PathVariable Long userId,@PathVariable Long addressId) throws CustomException {
        return new ResponseEntity<>(addressService.findAddressById(userId,addressId),HttpStatus.OK);

    }


    //ORDER

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<OrderResponse>> findAllOrderHistory(@PathVariable Long userId) throws CustomException {
        return new ResponseEntity<>(orderService.findAllOrderByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/{userId}/history/{status}")
    public ResponseEntity<List<OrderResponse>> findAllOrderHistoryByStatus(@PathVariable Long userId, @PathVariable String status) throws CustomException {
        return new ResponseEntity<>(orderService.findAllOrderHistoryByStatus(userId,status), HttpStatus.OK);
    }


    @PutMapping("/{userId}/history/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId, @PathVariable Long userId) throws CustomException {
        return new ResponseEntity<>(orderService.cancelOrder(orderId,userId),HttpStatus.OK);
    }






}
