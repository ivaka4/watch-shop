package webproject.watchshop.model.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webproject.watchshop.model.entity.Product;
import webproject.watchshop.model.view.ProductViewModel;

import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductCategoryServiceModel {

    private Long id;
    private String category;
    private String description;
    private List<ProductViewModel> products;
}
