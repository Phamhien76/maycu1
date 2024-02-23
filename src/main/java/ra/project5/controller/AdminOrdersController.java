package ra.project5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.OrderPatchRequest;
import ra.project5.model.dto.response.OderDetailResponse;
import ra.project5.model.dto.response.OrderResponse;
import ra.project5.model.entity.EOrder;
import ra.project5.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/orders")
public class AdminOrdersController {
    @Autowired
    private OrderService orderService;

    //ORDER


    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAllOrder(){
        return new ResponseEntity<>(orderService.findAllOrder(), HttpStatus.OK);
    }

    @GetMapping("/{status}/orderStatus")
    public ResponseEntity<List<OrderResponse>> findAllByStatus(@PathVariable String status){
        return new ResponseEntity<>(orderService.findAllOrderByStatus(status),HttpStatus.OK);

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<List<OderDetailResponse>> findAllOrderDetail(@PathVariable Long orderId){
        return new ResponseEntity<>(orderService.findAllOrderDetail(orderId),HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<OrderResponse> updateOrderStatus(@RequestBody OrderPatchRequest orderPatchRequest ) throws CustomException {
        return new ResponseEntity<>(orderService.updateOrderStatus(orderPatchRequest),HttpStatus.OK);
    }
}
