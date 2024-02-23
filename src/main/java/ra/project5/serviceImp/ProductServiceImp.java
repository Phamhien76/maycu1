package ra.project5.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ra.project5.advice.CustomException;
import ra.project5.model.dto.request.ProductPatchRequest;
import ra.project5.model.dto.request.ProductPostRequest;
import ra.project5.model.dto.request.ProductPutRequest;
import ra.project5.model.dto.response.ProductResponse;
import ra.project5.model.dto.response.UserResponse;
import ra.project5.model.entity.Categories;
import ra.project5.model.entity.Product;
import ra.project5.repository.CategoriesRepository;
import ra.project5.repository.ProductRepository;
import ra.project5.service.ProductService;


import java.util.*;

@Service
public class ProductServiceImp implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoriesRepository categoriesRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Map<String,Object> findAll(Pageable pageable) {
        Page<Product> pageProduct = productRepository.findAll(pageable);
        int totalPage = pageProduct.getTotalPages();
        List<Product> listProduct = pageProduct.getContent();
        List<ProductResponse> listProductResponse = listProduct.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class)).toList();
        Map<String,Object> data = new HashMap<>();
        data.put("totalPage", totalPage);
        data.put("product",listProductResponse);
        return data;
    }

    @Override
    public ProductResponse findById(Long productId) throws CustomException {
        Optional<Product> optProduct = productRepository.findById(productId);
        if (optProduct.isPresent()){
            return modelMapper.map(optProduct.get(), ProductResponse.class);
        }
        throw new CustomException("ProductId not found");
    }

    @Override
    public ProductResponse save(ProductPostRequest productPostRequest) throws CustomException {
//        Product product = modelMapper.map(productPostRequest, Product.class);
        Product product = Product.builder()
                .sku(productPostRequest.getSku())
                .productName(productPostRequest.getProductName())
                .description(productPostRequest.getDescription())
                .unitPrice(productPostRequest.getUnitPrice())
                .stockQuantity(productPostRequest.getStockQuantity())
                .image(productPostRequest.getImage())
                .build();
        product.setCreatedAt(new Date());
        product.setProductStatus(true);
        if (productRepository.existsByProductName(productPostRequest.getProductName())){

            throw new CustomException("ProductName exist");
        }
        if (productPostRequest.getStockQuantity()<=0){
            throw new CustomException("StockQuantity must be greater than 0.");
        }
        Categories categories = categoriesRepository.findById(productPostRequest.getCatalogId())
                .orElseThrow(() -> new CustomException("CatalogId not found"));
        product.setCatalog(categories);
        product.setSoldCount(0L);
        product.setWishCount(0L);
        Product newProduct= productRepository.save(product);
        return modelMapper.map(newProduct, ProductResponse.class);
    }

    @Override
    public ProductResponse update(ProductPutRequest productPutRequest) throws CustomException {
        boolean checkExist = productRepository.existsById(productPutRequest.getProductId());
        if (checkExist){
            Product product = modelMapper.map(productPutRequest, Product.class);
            product.setUpdatedAt(new Date());
            if (productRepository.existsByProductName(productPutRequest.getProductName())){

                throw new CustomException("ProductName exist");
            }
            if (productPutRequest.getStockQuantity()<=0){
                throw new CustomException("StockQuantity phải lớn hơn 0.");
            }
            Categories categories = categoriesRepository.findById(productPutRequest.getCatalogId())
                    .orElseThrow(() -> new CustomException("CatalogId not found"));
            product.setCatalog(categories);
            Product updateProduct= productRepository.save(product);
            return modelMapper.map(updateProduct, ProductResponse.class);
        }
        throw new CustomException("ProductId not found");
    }

    @Override
    public ProductResponse block(ProductPatchRequest productPatchRequest) throws CustomException {
        boolean checkExist = productRepository.existsById(productPatchRequest.getProductId());
        if (checkExist){
            Product productBlock = productRepository.findById(productPatchRequest.getProductId())
                    .orElseThrow(() -> new CustomException("ProductId not found"));
            productBlock.setProductStatus(!productBlock.isProductStatus());
            return modelMapper.map(productRepository.save(productBlock), ProductResponse.class);
        }
        throw new CustomException("ProductId not found");
    }

    @Override
    public List<ProductResponse> findByNameOrDescription(String name) {
       List<Product> products = productRepository.findAllByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(name,name);
        return products.stream().map(item->modelMapper.map(item,ProductResponse.class)).toList();

    }

    @Override
    public Map<String,Object> findAllStatus(Pageable pageable) {
        Page<Product> pageProduct = productRepository.findAllByProductStatusIsTrue(pageable);
        int totalPage = pageProduct.getTotalPages();
        List<Product> listProduct = pageProduct.getContent();
        List<ProductResponse> listProductResponse = listProduct.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class)).toList();
        Map<String,Object> data = new HashMap<>();
        data.put("totalPage", totalPage);
        data.put("product",listProductResponse);
        return data;
    }

    @Override
    public List<ProductResponse> findAllByCatalog(Long catalogId) throws CustomException {
        List<Product> listProduct = productRepository.findAllByCatalogCatalogId(catalogId);
        List<ProductResponse> listProductResponse = listProduct.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class)).toList();
        return listProductResponse;
    }

    @Override
    public List<ProductResponse> findNewProduct(String direction, String orderBy,int page,  int size) {

        Pageable pageable;
        if (direction.equals("ASC")) {
            pageable = PageRequest.of(page, size, Sort.by(orderBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(orderBy).descending());
        }
        Page<Product> pageProduct = productRepository.findAll(pageable);
        List<Product> listProduct = pageProduct.getContent();
        List<ProductResponse> listProductResponse = listProduct.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class)).toList();
        return listProductResponse;
    }

    @Override
    public List<ProductResponse> findTop5Product() {
        List<Product> listProduct=productRepository.findTop5ByOrderByCreatedAtDesc();
        List<ProductResponse> listProductResponse= listProduct.stream()
                .map(product -> modelMapper.map(product,ProductResponse.class)).toList();
        return listProductResponse;
    }
}
