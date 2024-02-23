package ra.project5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project5.model.entity.ERoles;
import ra.project5.model.entity.Roles;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles,Long> {
    Optional<Roles> findByName(ERoles eRoles);
}
