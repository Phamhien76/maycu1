package ra.project5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ra.project5.model.entity.ShoppingCart;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    ShoppingCart findByProductProductIdAndUserUserId(Long productId, Long userId);

    @Transactional
    void deleteAllByUserUserId(Long userId);

    List<ShoppingCart> findAllByUserUserId(Long userId);


}
