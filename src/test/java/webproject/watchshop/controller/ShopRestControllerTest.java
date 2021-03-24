package webproject.watchshop.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import webproject.watchshop.model.entity.Product;
import webproject.watchshop.model.entity.ProductCategory;
import webproject.watchshop.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ShopRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository mockProductRepository;

    private long TEST_PRODUCT1_ID, TEST_AUTHOR2_ID;
    private String TEST_PRODUCT1_NAME = "Rolex", TEST_AUTHOR2_NAME="Casio";

    @BeforeEach
    public void setUp(){
        Product product = getProduct();
        Product product2 = getProduct2();

        when(mockProductRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(mockProductRepository.findById(product2.getId())).thenReturn(Optional.of(product2));
        when(mockProductRepository.findAll()).thenReturn(List.of(product, product2));

        when(mockProductRepository.save(any())).thenAnswer(
                (Answer<Product>) invocation -> {
                    Product authorToSave = invocation.getArgument(0);
                    authorToSave.setId(3L);
                    return authorToSave;
                }
        );
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER","ADMIN"})
    public void testShopRestReturnCorrectStatusCodeAccess() throws Exception {
        this.mockMvc.perform(get("/shop/api")).andExpect(status().isOk());
    }

//    @Test
//    public void testShopRestReturnCorrectStatusCodeAccess() throws Exception {
//        this.mockMvc.perform(get("/shop/api")).andExpect(status().isOk());
//    }





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
    public Product getProduct2() {
        Product product = new Product();
        product.setId(2L);
        product.setEditedOn(LocalDateTime.now());
        product.setAddedOn(LocalDateTime.now());
        product.setPrice(BigDecimal.valueOf(10));
        product.setImageUrls(
                List.of("http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258871/qpy2zdazzvaawr8gucg8.jpg",
                "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258874/hwnk9rhmoqstkbl36j4z.jpg",
                        "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258606/wiqyq3bgigdkskgymdyz.jpg"));
        product.setModel("Roleey");
        product.setMake("Roleeey");
        product.setProductNumber("54321");
        product.setDescription("Producttt description");
        product.setName("My watch");
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategory("Oficial");
        productCategory.setDescription("Oficial");
        product.setCategory(productCategory);

        return product;
    }
}
