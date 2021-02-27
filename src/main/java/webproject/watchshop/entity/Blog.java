package webproject.watchshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "blogs")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Blog extends BaseEntity{
    @Column(nullable = false)
    private String title;
    @ManyToOne
    private User author;
    @OneToMany
    private List<Comment> comment;
    @ManyToOne
    private BlogCategory blogCategory;
    private String imgUrl;
    @Column(nullable = false)
    private LocalDate addedOn;
}
