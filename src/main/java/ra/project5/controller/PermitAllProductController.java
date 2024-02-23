package ra.project5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.response.*;
import ra.project5.service.OrderService;
import ra.project5.service.ProductService;
import ra.project5.service.WishListService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")

public class PermitAllProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WishListService wishListService;
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> findByNameOrDescription(
            @RequestParam(defaultValue = "") String name
    ){
        return new ResponseEntity<>(productService.findByNameOrDescription(name), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<Map<String,Object>> findAllByProductStatus(
            @PageableDefault(page = 0,size = 3,sort = "productName",direction = Sort.Direction.ASC) Pageable pageable

    ){
        return new ResponseEntity<>(productService.findAllStatus(pageable),HttpStatus.OK);
    }

    @GetMapping("/catalogs/{catalogId}")
    public ResponseEntity<List<ProductResponse>> findByCatalog(@PathVariable Long catalogId) throws CustomException {
        return new ResponseEntity<>(productService.findAllByCatalog(catalogId),HttpStatus.OK);
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long productId) throws CustomException {
        return new ResponseEntity<>(productService.findById(productId),HttpStatus.OK);
    }

    @GetMapping("/newProducts")
    public ResponseEntity<List<ProductResponse>> findNewProduct(    ){
        return new ResponseEntity<>(productService.findTop5Product(),HttpStatus.OK);

    }
    @GetMapping("/featuredProducts")
    public ResponseEntity<List<WishProductResponse>> findTopWishProduct(){
        return new ResponseEntity<>(wishListService.featuredProduct(),HttpStatus.OK);

    }
    @GetMapping("/bestSellerProducts")
    public ResponseEntity<List<BestSellerProductResponse>> findBestSellerProducts(){
        return new ResponseEntity<>(orderService.findBestSellerProducts(),HttpStatus.OK);

    }
}
