package webproject.watchshop.web.controller;

import org.junit.experimental.results.ResultMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import webproject.watchshop.WatchShopApplication;
import webproject.watchshop.model.entity.Product;
import webproject.watchshop.model.entity.ProductCategory;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.repository.ProductCategoryRepository;
import webproject.watchshop.repository.ProductRepository;
import webproject.watchshop.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Pattern;

import static org.mockito.ArgumentMatchers.matches;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = WatchShopApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductControllerTest {

    private static final String TEST_USER = "gosho";

    private long testProductId;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @BeforeEach
    public void setUp(){
        this.init();
    }

    @Test
    @WithMockUser(username="admin",authorities={"USER","ADMIN"})
    public void addProductTest() throws Exception {

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "photos",
                "file.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "This is the file content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/product/add")
                .file(mockMultipartFile)
                .param("name", "TestName")
                .param("description", "Test description")
                .param("make", "Test make")
                .param("model", "Test model")
                .param("productNumber", "Test productNumber")
                .param("price", "10")
                .param("category", "books")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(mvcResult -> {
                    "/shop".equals(mvcResult.getModelAndView().getViewName());
                });
    }

    @Test
    @WithMockUser(username=TEST_USER,authorities={"USER"})
    public void getAddProductPageWithoutAdminAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/product/add)").with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void getAddProductPageWhenNotLogged() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/product/add"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = TEST_USER, authorities = {"USER"})
    public void getProductDetailsPageWhenLogged() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/product/details/{id}", testProductId))
                .andExpect(status().isOk())
                .andExpect(view().name("product_details"))
                .andExpect(model().attributeExists("product"));
    }
    @Test
    @WithMockUser(username = TEST_USER, authorities = {"USER", "ADMIN"})
    public void getProductDetailsPageWhenLoggedWithAdminRole() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/product/details/{id}", testProductId))
                .andExpect(status().isOk())
                .andExpect(view().name("product_details"))
                .andExpect(model().attributeExists("product"));

    }

    private void init() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategory("METALLICA");
        productCategory.setDescription("Some info about metallica");
        productCategory = productCategoryRepository.save(productCategory);

        Product product = new Product();
        product.setName("Rolex");
        product.setDescription("The best");
        product.setMake("Make");
        product.setCategory(productCategory);
        product.setProductNumber("1421562");
        product.setModel("Model");
        product.setPrice(BigDecimal.valueOf(521));
        product.setAddedOn(LocalDateTime.now());
        product.setEditedOn(LocalDateTime.now());
        product.setImageUrls(List.of("http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258871/qpy2zdazzvaawr8gucg8.jpg",
                "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258874/hwnk9rhmoqstkbl36j4z.jpg",
                "http://res.cloudinary.com/watch-shop-cloud/image/upload/v1616258606/wiqyq3bgigdkskgymdyz.jpg"));
        product = productRepository.save(product);
        testProductId = product.getId();
    }

}
