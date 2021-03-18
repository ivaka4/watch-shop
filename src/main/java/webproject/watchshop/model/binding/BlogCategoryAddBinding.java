package webproject.watchshop.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class BlogCategoryAddBinding {

    @Length(min = 3, max =50, message = "Category name must be between 3 and 50 characters")
    @NotBlank
    private String name;

    @Length(min = 3, message = "Category description must be at least 3 characters")
    private String description;


}
