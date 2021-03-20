package webproject.watchshop.model.view;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webproject.watchshop.model.entity.ProductCategory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductViewModel {

    private Long id;
    private String name;
    private String description;
    private String make;
    private String model;
    private String productNumber;
    private BigDecimal price;
    private LocalDateTime addedOn;
    private LocalDateTime editedOn;
    private List<String> imageUrls;
    private ProductCategory category;

}
