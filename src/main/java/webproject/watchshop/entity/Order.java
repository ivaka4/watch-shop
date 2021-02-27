package webproject.watchshop.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order extends BaseEntity{
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private LocalDateTime buyDate;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false, unique = true)
    private String productNumber;
    @Column(nullable = false)
    private BigDecimal price;
//    @OneToMany()
//    private List<Product> products;
    @ManyToOne()
    private User user;
}
