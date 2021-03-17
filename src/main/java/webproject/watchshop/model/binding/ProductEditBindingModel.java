package webproject.watchshop.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProductEditBindingModel {

    private Long id;

    @NotBlank
    @Length(min = 3, max = 30, message = "Name must be between 3 and 30 characters")
    private String name;

    @NotBlank
    @Length(min = 3, message = "Description must be at least 3 characters")
    private String description;

    @NotBlank
    @Length(min = 3, max = 30, message = "Make must be between 3 and 30 characters")
    private String make;

    @NotBlank
    @Length(min = 3, max = 30, message = "Model must be between 3 and 30 characters")
    private String model;

    @NotBlank
    @Length(min = 3, max = 15, message = "Product number must be between 3 and 15 characters")
    private String productNumber;

    @NotNull
    @Min(value = 0, message = "Price must be positive number")
    private BigDecimal price;

    @NotNull
    @Min(value = 0, message = "Quantity must be positive number")
    private int quantity;


    private MultipartFile[] photos;
//
//    @NotNull
//    private List<String> imageUrls;

    @NotBlank
    private String category;

}
