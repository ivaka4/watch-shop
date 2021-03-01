package webproject.watchshop.service;

import webproject.watchshop.model.service.ProductCategoryServiceModel;

import java.util.List;

public interface ProductCategoryService {

    ProductCategoryServiceModel findProductCategory(String categoryName);

    List<ProductCategoryServiceModel> findAll();

}
