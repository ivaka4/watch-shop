package webproject.watchshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity{
    @ManyToOne
    private User author;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private LocalDateTime addedOn;
}
