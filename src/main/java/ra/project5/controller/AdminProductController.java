package ra.project5.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.ProductPatchRequest;
import ra.project5.model.dto.request.ProductPostRequest;
import ra.project5.model.dto.request.ProductPutRequest;
import ra.project5.model.dto.response.ProductResponse;
import ra.project5.service.ProductService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/product")
public class AdminProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> findAll(
            @PageableDefault(page = 0,size = 3,sort = "productName", direction = Sort.Direction.ASC)Pageable pageable
    ){
        return new ResponseEntity<>(productService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long productId) throws CustomException {
        return new ResponseEntity<>(productService.findById(productId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save (@Valid @RequestBody ProductPostRequest productPostRequest) throws CustomException {
        return new ResponseEntity<>(productService.save(productPostRequest),HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<ProductResponse> update(@Valid @RequestBody ProductPutRequest productPutRequest) throws CustomException {
        return new ResponseEntity<>(productService.update(productPutRequest),HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<ProductResponse> block(@RequestBody ProductPatchRequest productPatchRequest) throws CustomException {
        return new ResponseEntity<>(productService.block(productPatchRequest),HttpStatus.OK);
    }


}
