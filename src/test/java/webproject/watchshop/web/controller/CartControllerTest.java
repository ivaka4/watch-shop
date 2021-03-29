package webproject.watchshop.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import webproject.watchshop.WatchShopApplication;
import webproject.watchshop.enums.RoleEnum;
import webproject.watchshop.model.entity.*;
import webproject.watchshop.repository.ProductCategoryRepository;
import webproject.watchshop.repository.ProductRepository;
import webproject.watchshop.repository.UserRepository;
import webproject.watchshop.service.ProductCategoryService;
import webproject.watchshop.service.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = WatchShopApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CartControllerTest {

    private static final String TEST_USER = "gosho";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp(){
//        this.init();
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"USER"})
    public void getCartPageWhenLoggedInWithoutAdminAccess() throws Exception {
        mockMvc.perform(get("/cart").with(csrf())).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "pesho", authorities = {"USER", "ADMIN"})
    public void getCartWhenLoggedWithAdminAccess() throws Exception {
        mockMvc.perform(get("/cart").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("cart"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attributeExists("totalPrice"));
    }

    @Test
    public void postCartWhenNotLogged() throws Exception {
        mockMvc.perform(get("/cart")).andExpect(status().is3xxRedirection());
    }

    private void init() {

        Address address = new Address();
        address.setCity("Vidin");
        address.setAddress2("zhk Geo");
        address.setAddress1("zhk Geo");
        address.setPostCode("2412");
        address.setCountry("Bulgaria");

        Authority userRole = new Authority();
        userRole.setAuthority(RoleEnum.USER);
        Authority adminRole = new Authority();
        adminRole.setAuthority(RoleEnum.ADMIN);

//        userRole = authorityRepository.save(userRole);
//        adminRole = authorityRepository.save(adminRole);

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


        User userEntity = new User();
        userEntity.setUsername("pesho");
        userEntity.setPassword("xyz");
        userEntity.setFirstName("petar");
        userEntity.setLastName("petrov");
        userEntity.setProfilePicture("https://upload.wikimedia.org/wikipedia/en/b/bd/Metallica_-_...And_Justice_for_All_cover.jpg");
        userEntity.setEmail("abv@abv.bg");
        userEntity.setAddress(address);
        userEntity.setPhone(012522);
        userEntity.setRegisterOn(LocalDateTime.now());
        userEntity.setUpdatedOn(LocalDateTime.now());
        userEntity.setAuthorities(Set.of(userRole, adminRole));
        userEntity.setIsEnabled(true);
        userEntity.setCart(List.of(product));
        userEntity = userRepository.save(userEntity);
        System.out.println();
    }
}
