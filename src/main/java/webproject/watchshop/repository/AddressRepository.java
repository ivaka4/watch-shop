package webproject.watchshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webproject.watchshop.model.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
