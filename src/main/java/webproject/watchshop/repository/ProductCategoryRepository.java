package webproject.watchshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webproject.watchshop.model.entity.ProductCategory;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {


    Optional<ProductCategory> findByCategory(String name);
}
