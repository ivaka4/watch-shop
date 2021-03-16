package webproject.watchshop.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import webproject.watchshop.exceptions.productEx.ProductIdNotValid;
import webproject.watchshop.model.entity.Product;
import webproject.watchshop.model.entity.ProductCategory;
import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.repository.ProductRepository;
import webproject.watchshop.service.CloudinaryService;
import webproject.watchshop.service.ProductCategoryService;
import webproject.watchshop.service.ProductService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ProductCategoryService productCategoryService;
    private final CloudinaryService cloudinaryService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, ProductCategoryService productCategoryService, CloudinaryService cloudinaryService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.productCategoryService = productCategoryService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    @Transactional
    public ProductServiceModel uploadProduct(ProductServiceModel productServiceModel) throws IOException {
        MultipartFile[] img = productServiceModel.getPhotos();
        Product product = this.modelMapper.map(productServiceModel, Product.class);
        List<String> imgUrls = new LinkedList<>();
        for (MultipartFile multipartFile : img) {
            if (!multipartFile.isEmpty()) {
                String imgUploaded = cloudinaryService.uploadImage(multipartFile);
                imgUrls.add(imgUploaded);
            } else {
                imgUrls.add("http://localhost:8080/assets/img/gallery/gallery01.png");
                imgUrls.add("http://localhost:8080/assets/img/gallery/gallery2.png");
                imgUrls.add("http://localhost:8080/assets/img/gallery/gallery3.png");
            }
        }
        product.setImageUrls(imgUrls);
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
        if (productServiceModel == null) {
            throw new ProductIdNotValid("Cannot get product with this ID");
        }
        return productServiceModel;
    }

    @Override
    @Transactional
    public boolean removeProduct(Long id) {
        Product productToRemove = this.productRepository.findById(id).orElse(null);
        if (productToRemove == null) {
            throw new ProductIdNotValid("Product cannot be deleted");
        }
        this.productRepository.delete(productToRemove);
        return true;
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
