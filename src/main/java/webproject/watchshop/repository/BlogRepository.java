package webproject.watchshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webproject.watchshop.model.entity.Blog;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
}
