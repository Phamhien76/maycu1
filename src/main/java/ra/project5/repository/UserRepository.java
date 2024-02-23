package ra.project5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ra.project5.model.dto.response.UserResponse;
import ra.project5.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByUserName(String userName);
    boolean existsByPhone(String phone);
    Optional<User> findByUserNameAndStatus(String userName, boolean status);
    List<User> findAllByUserNameContainingIgnoreCaseOrFullNameContainingIgnoreCase(String userName, String fullName);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.token = null WHERE u.userId = :userId")
    void deleteTokenByUserId(Long userId);

}
