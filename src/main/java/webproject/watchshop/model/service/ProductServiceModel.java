package webproject.watchshop.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import webproject.watchshop.model.entity.ProductCategory;


import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductServiceModel{

    private Long id;
    private String name;
    private String description;
    private String make;
    private String model;
    private String productNumber;
    private BigDecimal price;
    private int quantity;
    private MultipartFile[] photos;
    private List<String> imageUrls;
    private String category;


}
