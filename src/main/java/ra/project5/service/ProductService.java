package ra.project5.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.ProductPatchRequest;
import ra.project5.model.dto.request.ProductPostRequest;
import ra.project5.model.dto.request.ProductPutRequest;
import ra.project5.model.dto.response.ProductResponse;
import ra.project5.model.entity.Categories;
import ra.project5.model.entity.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {
    Map<String,Object> findAll(Pageable pageable);
    ProductResponse findById(Long productId) throws CustomException;
    ProductResponse save (ProductPostRequest productPostRequest) throws CustomException;
    ProductResponse update(ProductPutRequest productPutRequest) throws CustomException;
    ProductResponse block(ProductPatchRequest productPatchRequest) throws CustomException;
    List<ProductResponse> findByNameOrDescription(String name);
    Map<String,Object> findAllStatus(Pageable pageable);
    List<ProductResponse> findAllByCatalog(Long catalogId) throws CustomException;
    List<ProductResponse> findNewProduct(String direction, String orderBy,int page, int size);

    List<ProductResponse> findTop5Product();



}
