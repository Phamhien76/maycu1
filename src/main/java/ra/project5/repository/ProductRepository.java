package ra.project5.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ra.project5.model.entity.Categories;
import ra.project5.model.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(String sku);
    boolean existsByProductName(String productName);
    List<Product> findAllByProductNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String productNane, String description);

    Page<Product> findAllByProductStatusIsTrue(Pageable pageable);
    List<Product> findAllByCatalogCatalogId(Long catalogId);
    Optional<Product> findByProductIdAndProductStatus(Long productId, boolean status);
    List<Product> findTop5ByOrderByCreatedAtDesc();


}
