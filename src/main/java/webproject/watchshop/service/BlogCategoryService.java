package webproject.watchshop.service;

import webproject.watchshop.model.service.BlogCategoryServiceModel;
import webproject.watchshop.model.view.BlogCategoryViewModel;

import java.util.List;

public interface BlogCategoryService {

    BlogCategoryServiceModel addBlogCategory(BlogCategoryServiceModel blogCategoryServiceModel);

    List<BlogCategoryViewModel> findAll();
}
