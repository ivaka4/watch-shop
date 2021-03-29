package webproject.watchshop.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import webproject.watchshop.exceptions.productEx.ProductCategoryNotSelected;
import webproject.watchshop.model.entity.Product;
import webproject.watchshop.model.entity.ProductCategory;
import webproject.watchshop.model.service.ProductCategoryServiceModel;
import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.model.view.ProductCategoryView;
import webproject.watchshop.model.view.ProductViewModel;
import webproject.watchshop.repository.ProductCategoryRepository;
import webproject.watchshop.repository.ProductRepository;
import webproject.watchshop.service.impl.ProductServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    @Mock
    private ProductCategoryRepository mockProductCategoryRepository;

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
    private ProductCategory productCategory;

    @BeforeEach
    public void setup() throws IOException {
        //this.mockedStoreRepository = Mockito.mock(StoreRepository.class);
        product = this.getProduct();
        productViewModel = this.getProductViewModel();
        productServiceModel = this.getProductServiceModel();
        productCategory = this.getProductCategory();
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
    public void testProductServiceGetProductById() {
        when(mockProductRepository.findById(any()))
                .thenReturn(Optional.ofNullable(product));
//        when(mockProductCategoryRepository.findById(any())).thenReturn(Optional.ofNullable(productCategory));

        ProductServiceModel serviceModel = new ProductServiceModel();
        serviceModel.setId(1L);
        serviceModel.setName("Rolex");
        serviceModel.setDescription("Stylish");
//        serviceModel.setCategory("Stylish");


        //Act
        ProductServiceModel result = productService.getProductBy(serviceModel.getId());

        //Assert
        Assertions.assertEquals(productServiceModel.getName(), result.getName());
        Assertions.assertEquals(productServiceModel.getId(), result.getId());
        Assertions.assertEquals(productServiceModel.getDescription(), result.getDescription());

    }

    @Test
    public void testProductServiceUploadProduct() throws IOException {
//        when(mockProductRepository.save(any()))
//                .thenReturn(Optional.ofNullable(product));
//        when(mockProductCategoryRepository.save(any())).thenReturn(Optional.ofNullable(productCategory));
        ProductCategoryServiceModel productCategory = new ProductCategoryServiceModel();
        productCategory.setCategory("Stylish");
        productCategory.setDescription("The best description");

        ProductServiceModel serviceModel = new ProductServiceModel();
        serviceModel.setId(1L);
        serviceModel.setName("Rolex");
        serviceModel.setDescription("Stylish");
        serviceModel.setEditedOn(LocalDateTime.now());
        serviceModel.setAddedOn(LocalDateTime.now());
        serviceModel.setPrice(BigDecimal.valueOf(250));
        serviceModel.setImageUrls(
                List.of("http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258871/qpy2zdazzvaawr8gucg8.jpg",
                        "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258874/hwnk9rhmoqstkbl36j4z.jpg",
                        "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258606/wiqyq3bgigdkskgymdyz.jpg"));
        File file = new File("C:\\Users\\Ivailo.DESKTOP-J380DFT\\Desktop\\IMG_20200408_212850.jpg");
        FileInputStream image1 = new FileInputStream(file);
        MultipartFile[] multipartFiles = new MultipartFile[3];

            multipartFiles[0] = new MockMultipartFile("file",
                    file.getName(), "image/jpg", image1.readAllBytes());
        multipartFiles[1] = new MockMultipartFile("file1",
                file.getName(), "image/jpg", image1.readAllBytes());
        multipartFiles[2] = new MockMultipartFile("file2",
                file.getName(), "image/jpg", image1.readAllBytes());

        serviceModel.setPhotos(multipartFiles);
        serviceModel.setModel("Casio");
        serviceModel.setMake("Casio");
        serviceModel.setProductNumber("12345");
        serviceModel.setDescription("Product description");
        serviceModel.setName("Rolex");
        serviceModel.setCategory("Stylish");


        //Act
        ProductCategoryServiceModel productCategoryServiceModel = productCategoryService.addCategory(productCategory);
                System.out.println();
        ProductServiceModel result = productService.uploadProduct(serviceModel);
        //Assert
        Assertions.assertEquals(productServiceModel.getName(), result.getName());
        Assertions.assertEquals(productServiceModel.getId(), result.getId());
        Assertions.assertEquals(productServiceModel.getDescription(), result.getDescription());
        Assertions.assertEquals(productServiceModel.getCategory(), productCategory.getCategory());

    }

    @Test()
    public void testProductServiceUploadProductWhenNotSelectedCategoryShouldThrowError() throws IOException {
        ProductCategoryServiceModel productCategory = new ProductCategoryServiceModel();
        productCategory.setCategory("Stylish");
        productCategory.setDescription("The best description");

        ProductServiceModel serviceModel = new ProductServiceModel();
        serviceModel.setId(1L);
        serviceModel.setName("Rolex");
        serviceModel.setDescription("Stylish");
        serviceModel.setEditedOn(LocalDateTime.now());
        serviceModel.setAddedOn(LocalDateTime.now());
        serviceModel.setPrice(BigDecimal.valueOf(250));
        serviceModel.setImageUrls(
                List.of("http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258871/qpy2zdazzvaawr8gucg8.jpg",
                        "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258874/hwnk9rhmoqstkbl36j4z.jpg",
                        "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258606/wiqyq3bgigdkskgymdyz.jpg"));
        File file = new File("C:\\Users\\Ivailo.DESKTOP-J380DFT\\Desktop\\IMG_20200408_212850.jpg");
        FileInputStream image1 = new FileInputStream(file);
        MultipartFile[] multipartFiles = new MultipartFile[3];

        multipartFiles[0] = new MockMultipartFile("file",
                file.getName(), "image/jpg", image1.readAllBytes());
        multipartFiles[1] = new MockMultipartFile("file1",
                file.getName(), "image/jpg", image1.readAllBytes());
        multipartFiles[2] = new MockMultipartFile("file2",
                file.getName(), "image/jpg", image1.readAllBytes());

        serviceModel.setPhotos(multipartFiles);
        serviceModel.setModel("Casio");
        serviceModel.setMake("Casio");
        serviceModel.setProductNumber("12345");
        serviceModel.setDescription("Product description");
        serviceModel.setName("Rolex");
        serviceModel.setCategory("Stylish");


        //Act
//        ProductCategoryServiceModel productCategoryServiceModel = productCategoryService.addCategory(productCategory);
        System.out.println();
//        ProductServiceModel result = productService.uploadProduct(serviceModel);
        //Assert
        Assertions.assertThrows(
                ProductCategoryNotSelected.class, () -> {
                    productService.uploadProduct(productServiceModel);
                }
        );

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
    public ProductCategory getProductCategory() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategory("Stylish");
        productCategory.setDescription("Best category");

        return productCategory;
    }

    public ProductServiceModel getProductServiceModel() throws IOException {
        ProductServiceModel product = new ProductServiceModel();
        product.setId(1L);
        product.setEditedOn(LocalDateTime.now());
        product.setAddedOn(LocalDateTime.now());
        product.setPrice(BigDecimal.valueOf(250));
        product.setImageUrls(
                List.of("http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258871/qpy2zdazzvaawr8gucg8.jpg",
                        "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258874/hwnk9rhmoqstkbl36j4z.jpg",
                        "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258606/wiqyq3bgigdkskgymdyz.jpg"));
        File file = new File("C:\\Users\\Ivailo.DESKTOP-J380DFT\\Desktop\\IMG_20200408_212850.jpg");
        FileInputStream image1 = new FileInputStream(file);
        MultipartFile[] multipartFiles = new MultipartFile[3];

        multipartFiles[0] = new MockMultipartFile("file",
                file.getName(), "image/jpg", image1.readAllBytes());
        multipartFiles[1] = new MockMultipartFile("file2",
                file.getName(), "image/jpg", image1.readAllBytes());
        multipartFiles[2] = new MockMultipartFile("file3",
                file.getName(), "image/jpg", image1.readAllBytes());
        product.setPhotos(multipartFiles);
        product.setModel("Casio");
        product.setMake("Casio");
        product.setProductNumber("12345");
        product.setDescription("Product description");
        product.setName("Rolex");
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
