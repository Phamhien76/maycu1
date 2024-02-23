package ra.project5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ra.project5.model.entity.EOrder;
import ra.project5.model.entity.Order;

import java.sql.Date;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStatusIs(EOrder status);
    List<Order> findAllByUserUserId(Long userId);
    List<Order> findAllByUserUserIdAndStatus(Long userId, EOrder status);

    @Query("SELECT o FROM Order o WHERE o.status = :status and o.createdAt BETWEEN :from AND :to")
    List<Order> salesTotalPrice(Date from, Date to, EOrder status);



}
