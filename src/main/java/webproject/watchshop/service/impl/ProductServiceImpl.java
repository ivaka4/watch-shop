package webproject.watchshop.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webproject.watchshop.exceptions.productEx.ProductIdNotValid;
import webproject.watchshop.model.entity.Product;
import webproject.watchshop.model.entity.ProductCategory;
import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.repository.ProductRepository;
import webproject.watchshop.service.ProductCategoryService;
import webproject.watchshop.service.ProductService;

import java.util.List;
import java.util.Optional;

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
//        if (productServiceModel.getCategory() == null){
//            throw new Exception("Make custom exception");
//        }
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

    @Override
    @Transactional
    public ProductServiceModel getProductBy(Long id) {
        ProductServiceModel productServiceModel = this.modelMapper.map(this.productRepository
                .findById(id).orElse(null), ProductServiceModel.class);
        if (productServiceModel == null){
            throw new ProductIdNotValid("Cannot get product with this ID");
        }
        return productServiceModel;
    }
    private static ProductServiceModel mapToSummary(Product offerEntity) {
        ProductServiceModel offerModel = new ProductServiceModel();
        mapToSummary(offerEntity, offerModel);
        return offerModel;
    }

    private static void mapToSummary(Product offerEntity, ProductServiceModel offerModel) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(offerEntity, offerModel);
    }

    private static ProductServiceModel mapToDetails(Product offerEntity) {
        ProductServiceModel offerModel = new ProductServiceModel();
        mapToSummary(offerEntity, offerModel);
//    offerModel.
//        setSellerFirstName(offerEntity.getSeller().getFirstName()).
//        setSellerLastName(offerEntity.getSeller().getLastName());
        return offerModel;
    }
}
