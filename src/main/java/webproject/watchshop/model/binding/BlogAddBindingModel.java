package webproject.watchshop.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class BlogAddBindingModel {

    @NotBlank(message = "Can`t be empty")
    @Length(min = 3, max = 55, message = "Title must be between 3 and 55 characters")
    private String title;
    @NotBlank(message = "Can`t be empty")
    @Length(min = 3, message = "Description must be at least 3 characters")
    private String description;
    @NotBlank(message = "Must select category")
    private String category;

    private MultipartFile photo;
}
