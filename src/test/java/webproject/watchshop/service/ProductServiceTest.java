package webproject.watchshop.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import webproject.watchshop.model.entity.Product;
import webproject.watchshop.model.entity.ProductCategory;
import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.model.view.ProductCategoryView;
import webproject.watchshop.model.view.ProductViewModel;
import webproject.watchshop.repository.ProductRepository;
import webproject.watchshop.service.impl.ProductServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository mockProductRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private CloudinaryService cloudinaryService;

    private Product product;
    private ProductServiceModel productServiceModel;
    private ProductViewModel productViewModel;

    @BeforeEach
    public void setup() {
        //this.mockedStoreRepository = Mockito.mock(StoreRepository.class);
        product = this.getProduct();
        productViewModel = this.getProductViewModel();
        productServiceModel = this.getProductServiceModel();
        this.productService = new ProductServiceImpl(mockProductRepository,
                this.modelMapper, this.productCategoryService, this.cloudinaryService);

    }


    @Test
    public void testProductServiceGetAllProducts() {
        when(productService.getAllProducts()).thenReturn(List.of(productServiceModel));

        List<ProductServiceModel> psm = productService.getAllProducts();
        ProductServiceModel actualDto = psm.get(0);

        Assertions.assertEquals(productServiceModel.getName(), actualDto.getName());
        Assertions.assertEquals(productServiceModel.getProductNumber(), actualDto.getProductNumber());

    }

    @Test
    public void testProductServiceGetProductById(){

        when(productService.getProductBy(1L)).thenReturn(productServiceModel);

        ProductServiceModel psvm = productService.getProductBy(1L);

        Assertions.assertEquals(productServiceModel.getId(), psvm.getId());
        Assertions.assertEquals(productServiceModel.getName(), psvm.getName());


    }


    public Product getProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setEditedOn(LocalDateTime.now());
        product.setAddedOn(LocalDateTime.now());
        product.setPrice(BigDecimal.valueOf(250));
        product.setImageUrls(
                List.of("http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258871/qpy2zdazzvaawr8gucg8.jpg",
                        "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258874/hwnk9rhmoqstkbl36j4z.jpg",
                        "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258606/wiqyq3bgigdkskgymdyz.jpg"));
        product.setModel("Casio");
        product.setMake("Casio");
        product.setProductNumber("12345");
        product.setDescription("Product description");
        product.setName("Rolex");
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategory("Stylish");
        productCategory.setDescription("Stylish");
        product.setCategory(productCategory);

        return product;
    }

    public ProductServiceModel getProductServiceModel() {
        ProductServiceModel product = new ProductServiceModel();
        product.setId(1L);
        product.setEditedOn(LocalDateTime.now());
        product.setAddedOn(LocalDateTime.now());
        product.setPrice(BigDecimal.valueOf(250));
        product.setImageUrls(
                List.of("http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258871/qpy2zdazzvaawr8gucg8.jpg",
                        "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258874/hwnk9rhmoqstkbl36j4z.jpg",
                        "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258606/wiqyq3bgigdkskgymdyz.jpg"));
        MultipartFile[] multipartFiles = new MultipartFile[3];
        product.setPhotos(multipartFiles);
        product.setModel("Casio");
        product.setMake("Casio");
        product.setProductNumber("12345");
        product.setDescription("Product description");
        product.setName("Rolex");
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategory("Stylish");
        productCategory.setDescription("Stylish");
        product.setCategory("Stylish");

        return product;
    }

    public ProductViewModel getProductViewModel() {
        ProductViewModel product = new ProductViewModel();
        product.setId(1L);
        product.setEditedOn(LocalDateTime.now());
        product.setAddedOn(LocalDateTime.now());
        product.setPrice(BigDecimal.valueOf(250));
        product.setImageUrls(
                List.of("http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258871/qpy2zdazzvaawr8gucg8.jpg",
                        "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258874/hwnk9rhmoqstkbl36j4z.jpg",
                        "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258606/wiqyq3bgigdkskgymdyz.jpg"));
        product.setModel("Casio");
        product.setMake("Casio");
        product.setProductNumber("12345");
        product.setDescription("Product description");
        product.setName("Rolex");
        ProductCategoryView productCategory = new ProductCategoryView();
        productCategory.setCategory("Stylish");
        productCategory.setDescription("Stylish");
        product.setCategory(productCategory);

        return product;
    }


}
