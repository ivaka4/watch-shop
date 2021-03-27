package webproject.watchshop.web.controller;

import org.junit.jupiter.api.Test;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import webproject.watchshop.WatchShopApplication;
import webproject.watchshop.service.ProductCategoryService;
import webproject.watchshop.service.UserService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


    @SpringBootTest
    @AutoConfigureMockMvc
    @ContextConfiguration(classes = WatchShopApplication.class)
    @AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
    public class ProductCategoryControllerTest {

        private static final String TEST_USER = "gosho";

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ProductCategoryService productCategoryService;

        @Test
        @WithMockUser(username="admin",authorities={"USER","ADMIN"})
        public void addProductCategory() throws Exception {

            mockMvc.perform(MockMvcRequestBuilders
                    .post("/category/add")
                    .param("category", "TestName")
                    .param("description", "Test description")
                    .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(mvcResult -> {
                        "/product/add".equals(mvcResult.getModelAndView().getViewName());
                    });
        }

        @Test
        @WithMockUser(username="admin",authorities={"USER"})
        public void testGetProductCategoryAddWithoutAdminAccess() throws Exception {

            mockMvc.perform(get("/category/add"))
                    .andExpect(status().is4xxClientError());
        }

        @Test
        public void testGetProductCategoryAddNotLoggedIN() throws Exception {

            mockMvc.perform(get("/category/add"))
                    .andExpect(status().is3xxRedirection());
        }

    }
