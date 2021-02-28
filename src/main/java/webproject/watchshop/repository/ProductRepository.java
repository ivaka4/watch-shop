package webproject.watchshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webproject.watchshop.model.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
