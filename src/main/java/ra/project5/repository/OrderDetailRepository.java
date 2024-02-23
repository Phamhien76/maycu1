package ra.project5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.project5.model.dto.response.BestSellerProductResponse;
import ra.project5.model.dto.response.CategoriesSales;
import ra.project5.model.dto.response.ProductBestSellerProducts;
import ra.project5.model.entity.OderDetail;
import ra.project5.model.entity.Product;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OderDetail, Long> {
    List<OderDetail> findAllByOrderOrderId(Long orderId);
    @Query("SELECT new ra.project5.model.dto.response.BestSellerProductResponse(od.product.productId, od.product.productName,SUM (od.orderQuantity))  FROM OderDetail od GROUP BY od.product.productId ORDER BY SUM (od.orderQuantity)DESC ")
    List<BestSellerProductResponse> findBestSellerProducts();

    @Query("SELECT new ra.project5.model.dto.response.ProductBestSellerProducts(od.product.productId,od.product.productName,SUM (od.orderQuantity)) FROM OderDetail od JOIN od.order o " +
            "WHERE YEAR (o.createdAt) = YEAR (CURRENT_DATE ) AND MONTH (o.createdAt) = MONTH (CURRENT_DATE )" +
            "GROUP BY od.product ORDER BY SUM (od.orderQuantity) DESC " )
    List<ProductBestSellerProducts> findBestSellerProductsOfMonth();

//    @Query("SELECT new ra.project5.model.dto.response.CategoriesSales(c.catalogName, SUM (od.unitPrice * od.orderQuantity)) FROM OderDetail  od JOIN Product p ON od.product.productId = p.productId JOIN Categories c ON p.catalog.catalogId = c.catalogId  GROUP BY  c.catalogId" );
//    List<CategoriesSales> findCatalogSale();





}
