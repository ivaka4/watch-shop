package webproject.watchshop.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webproject.watchshop.exceptions.blogEx.BlogCategoryNotValid;
import webproject.watchshop.model.entity.BlogCategory;
import webproject.watchshop.model.service.BlogCategoryServiceModel;
import webproject.watchshop.repository.BlogCategoryRepository;
import webproject.watchshop.service.BlogCategoryService;

@Service
public class BlogCategoryServiceImpl implements BlogCategoryService {
    private final BlogCategoryRepository blogCategoryRepository;
    private final ModelMapper modelMapper;

    public BlogCategoryServiceImpl(BlogCategoryRepository blogCategoryRepository, ModelMapper modelMapper) {
        this.blogCategoryRepository = blogCategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public BlogCategoryServiceModel addBlogCategory(BlogCategoryServiceModel blogCategoryServiceModel) {
        BlogCategory blogCategory = this.modelMapper.map(blogCategoryServiceModel, BlogCategory.class);
        if (blogCategory == null){
            throw new BlogCategoryNotValid("Blog category cannot be added");
        }
        this.blogCategoryRepository.saveAndFlush(blogCategory);
        return this.modelMapper.map(blogCategory, BlogCategoryServiceModel.class);
    }
}
