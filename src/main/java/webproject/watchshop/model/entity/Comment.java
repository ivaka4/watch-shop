package webproject.watchshop.model.entity;

import webproject.watchshop.constants.SCHEMA;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments", schema = SCHEMA.NAME)
public class Comment extends BaseEntity{
    @ManyToOne
    private User author;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private LocalDateTime addedOn;
}
