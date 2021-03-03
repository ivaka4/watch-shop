package webproject.watchshop.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webproject.watchshop.model.entity.Product;
import webproject.watchshop.model.entity.ProductCategory;
import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.repository.ProductRepository;
import webproject.watchshop.service.ProductCategoryService;
import webproject.watchshop.service.ProductService;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ProductCategoryService productCategoryService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ProductCategoryService productCategoryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.productCategoryService = productCategoryService;
    }

    @Override
    @Transactional
    public ProductServiceModel uploadProduct(ProductServiceModel productServiceModel) {
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        ProductCategory productCategory = this.modelMapper.map(productCategoryService
                .findProductCategory(productServiceModel.getCategory()), ProductCategory.class);
        product.setCategory(productCategory);
        this.productRepository.saveAndFlush(product);
        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    @Transactional
    public List<ProductServiceModel> getAllProducts() {
        return this.modelMapper.map(this.productRepository.findAll(), new TypeToken<List<ProductServiceModel>>() {
        }.getType());
    }
}
