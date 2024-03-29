package webproject.watchshop.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webproject.watchshop.constants.SCHEMA;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "blog_category", schema = SCHEMA.NAME)
@Getter
@Setter
@NoArgsConstructor
public class BlogCategory extends BaseEntity {
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String description;
    @OneToMany
    private List<Blog> blogs;
}
