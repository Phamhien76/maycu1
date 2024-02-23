package ra.project5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ra.project5.model.entity.Categories;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {
    boolean existsByCatalogName(String catalogName);




    List<Categories> findAllByCatalogStatusTrue();
}
