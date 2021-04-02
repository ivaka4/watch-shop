package webproject.watchshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import webproject.watchshop.model.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p ORDER BY p.addedOn desc")
    List<Product> findLatest();

    @Query(value = "SELECT p FROM Product p ORDER BY p.price")
    List<Product> findAllByPrice();
}
