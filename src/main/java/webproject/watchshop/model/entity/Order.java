package webproject.watchshop.model.entity;

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
    @Column(name = "description", nullable = false)
    private String productDescription;
    @Column(name = "make", nullable = false)
    private String productMake;
    @Column(name = "model", nullable = false)
    private String productModel;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false, unique = true)
    private String productNumber;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(name = "order_images")
    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    private List<String> orderImages;

    @Column(nullable = false)
    private String productCategory;
    @Column(nullable = false, unique = true)
    private Long productId;
//    @OneToMany()
//    private List<Product> products;
    @ManyToOne()
    private User user;
}
