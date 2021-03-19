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
public class OrderViewModel {

    private Long id;
    private String firstName;
    private String lastName;
    private String productName;
    private String productNumber;
    private LocalDateTime buyDate;
    private BigDecimal price;
    private List<String> orderImages;

}
