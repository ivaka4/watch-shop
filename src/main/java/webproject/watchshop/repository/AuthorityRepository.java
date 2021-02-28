package webproject.watchshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webproject.watchshop.enums.RoleEnum;
import webproject.watchshop.model.entity.Authority;

import java.util.Set;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Set<Authority> findAllByAuthority(RoleEnum role);
}
