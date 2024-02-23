package ra.project5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project5.model.entity.Address;
import ra.project5.model.entity.ShoppingCart;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
   List<Address> findAllByUserUserId(Long userId);

}
