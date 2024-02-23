package ra.project5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ra.project5.model.dto.response.WishProductResponse;
import ra.project5.model.entity.Product;
import ra.project5.model.entity.WishList;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList,Long > {
    @Query("SELECT new ra.project5.model.dto.response.WishProductResponse(w.product.productId,w.product.productName, COUNT (w.user))  FROM WishList  w GROUP BY  w.product.productId ORDER BY COUNT (w.user) DESC ")
    List<WishProductResponse> featuredProducts();

}
