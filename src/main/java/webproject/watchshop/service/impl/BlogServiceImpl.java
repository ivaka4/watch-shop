package webproject.watchshop.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webproject.watchshop.exceptions.blogEx.BlogAddException;
import webproject.watchshop.model.entity.Blog;
import webproject.watchshop.model.entity.BlogCategory;
import webproject.watchshop.model.entity.User;
import webproject.watchshop.model.service.BlogServiceModel;
import webproject.watchshop.repository.BlogCategoryRepository;
import webproject.watchshop.repository.BlogRepository;
import webproject.watchshop.service.BlogService;
import webproject.watchshop.service.CloudinaryService;
import webproject.watchshop.service.UserService;

import java.io.IOException;
import java.time.LocalDate;

@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final BlogCategoryRepository blogCategoryRepository;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository, UserService userService, ModelMapper modelMapper, BlogCategoryRepository blogCategoryRepository, CloudinaryService cloudinaryService) {
        this.blogRepository = blogRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.blogCategoryRepository = blogCategoryRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    @Transactional
    public BlogServiceModel addBlog(BlogServiceModel blogServiceModel, String loggedUser) throws IOException {
        Blog blog = this.modelMapper.map(blogServiceModel, Blog.class);
        User author = this.modelMapper.map(this.userService.findByUsername(loggedUser), User.class);
        BlogCategory blogCategory = this
                .blogCategoryRepository
                .findBlogCategoryByCategory(blogServiceModel.getCategory()).orElse(null);
        if (blog == null || author == null || blogCategory == null) {
            throw new BlogAddException("Blog can`t be added! Unauthorized user, wrong input or not selected category");
        }
        blog.setAddedOn(LocalDate.now());
        blog.setAuthor(author);
        String imgUrl = this.cloudinaryService.uploadImage(blogServiceModel.getPhoto());
        blog.setImgUrl(imgUrl);
        blog.setCategory(blogCategory);
        this.blogRepository.saveAndFlush(blog);
        return this.modelMapper.map(blog, BlogServiceModel.class);
    }
}
