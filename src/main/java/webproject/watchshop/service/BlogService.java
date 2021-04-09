package webproject.watchshop.service;

import webproject.watchshop.model.service.BlogServiceModel;
import webproject.watchshop.model.view.BlogViewModel;

import java.io.IOException;
import java.util.List;

public interface BlogService {

    BlogServiceModel addBlog(BlogServiceModel blogServiceModel, String loggedUser) throws IOException;

    List<BlogViewModel> findAll();

    BlogServiceModel getBlogById(Long id);
}
