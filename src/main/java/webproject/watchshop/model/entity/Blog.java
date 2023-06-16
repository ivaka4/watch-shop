package webproject.watchshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webproject.watchshop.constants.SCHEMA;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "blogs", schema = SCHEMA.NAME)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Blog extends BaseEntity {
    @Column(nullable = false)
    private String title;
    @Column(nullable = false, columnDefinition = "TEXT", length = 1000000)
    private String description;
    @ManyToOne
    private User author;
    @OneToMany
    private List<Comment> comment;
    @ManyToOne
    private BlogCategory category;
    private String imgUrl;
    @Column(nullable = false)
    private LocalDate addedOn;
}
