package webproject.watchshop.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryBindingModel {
    @NotNull
    private String category;
    @NotNull
    private String description;
}
