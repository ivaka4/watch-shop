package webproject.watchshop.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import webproject.watchshop.model.entity.Product;
import webproject.watchshop.model.entity.ProductCategory;
import webproject.watchshop.model.service.ProductServiceModel;
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
public class ProductServiceTest {

    @MockBean
    private ProductRepository mockProductRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private  ProductCategoryService productCategoryService;

    @Autowired
    private  CloudinaryService cloudinaryService;

    private Product product;
    private ProductServiceModel productServiceModel;

    @BeforeEach
    public void setup() {
        //this.mockedStoreRepository = Mockito.mock(StoreRepository.class);
       product = this.getProduct();
       productServiceModel = this.getProductServiceModel();
        this.productService = new ProductServiceImpl(this.mockProductRepository,
                this.modelMapper, this.productCategoryService, this.cloudinaryService);

    }


    @Test
    public void testGetProductById() throws IOException {
        when(mockProductRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Product created = this.mockProductRepository.findById(1L).orElse(null);
        System.out.println();
        assert created != null;
        Assert.assertEquals(product.getId(), created.getId());

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
    } public ProductServiceModel getProductServiceModel() {
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


}
