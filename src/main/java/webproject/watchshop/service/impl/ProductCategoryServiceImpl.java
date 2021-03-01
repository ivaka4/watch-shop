package webproject.watchshop.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import webproject.watchshop.model.entity.ProductCategory;
import webproject.watchshop.model.service.ProductCategoryServiceModel;
import webproject.watchshop.repository.ProductCategoryRepository;
import webproject.watchshop.service.ProductCategoryService;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
    private final ProductCategoryRepository productCategoryRepository;
    private final ModelMapper modelMapper;

    public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepository, ModelMapper modelMapper) {
        this.productCategoryRepository = productCategoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductCategoryServiceModel findProductCategory(String categoryName) {
        ProductCategory productCategory = this.productCategoryRepository.findByCategory(categoryName).orElse(null);
        return this.modelMapper.map(productCategory,
                ProductCategoryServiceModel.class);
    }

    @Override
    public List<ProductCategoryServiceModel> findAll() {
        return this.modelMapper.map(productCategoryRepository.findAll(),
                new TypeToken<List<ProductCategoryServiceModel>>() {
                }.getType());
    }
}