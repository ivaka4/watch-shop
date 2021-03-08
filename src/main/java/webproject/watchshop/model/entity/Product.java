package webproject.watchshop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "make", nullable = false)
    private String make;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "product_number", nullable = false, unique = true)
    private String productNumber;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    @Column(name = "quantity", nullable = false)
    private int quantity;
    @Column(name = "image_urls")
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> imageUrls;
    @ManyToOne()
    private ProductCategory category;

}
