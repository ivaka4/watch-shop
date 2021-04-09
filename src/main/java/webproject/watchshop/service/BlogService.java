package webproject.watchshop.service;

import webproject.watchshop.model.service.BlogServiceModel;

import java.io.IOException;

public interface BlogService {

    BlogServiceModel addBlog(BlogServiceModel blogServiceModel, String loggedUser) throws IOException;

}
