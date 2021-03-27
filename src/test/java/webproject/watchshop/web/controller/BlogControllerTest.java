package webproject.watchshop.web.controller;

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
import webproject.watchshop.service.ProductCategoryService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = WatchShopApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BlogControllerTest {

    private static final String TEST_USER = "gosho";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductCategoryService productCategoryService;

    @Test
    @WithMockUser(username="admin",authorities={"USER","ADMIN"})
    public void getBlogPageWhenLoggedInWithAdminAccess() throws Exception {
        mockMvc.perform(get("/blog")).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username="admin",authorities={"USER","ADMIN"})
    public void postBlogCategoryWhenLoggedWithAdminAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/blog/category/add")
                .param("name", "TestName")
                .param("description", "Test Blog Description")
        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(mvcResult -> {
                    "/blog".equals(mvcResult.getModelAndView().getViewName());
        });
    }
    @Test
    @WithMockUser(username="admin",authorities={"USER"})
    public void postBlogCategoryWhenLoggedWithoutAdminAccess() throws Exception {
        mockMvc.perform(get("/blog/category/add")
                .with(csrf()))
                .andExpect(status().is4xxClientError());

    }
    @Test
    public void postBlogCategoryWhenNotLogged() throws Exception {
        mockMvc.perform(get("/blog/category/add")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username="admin",authorities={"USER"})
    public void getBlogPageWhenLoggedInWithoutAdminAccess() throws Exception {
        mockMvc.perform(get("/blog")).andExpect(status().isOk());
    }

    @Test
    public void getBlogPageWhenNotLoggedIn() throws Exception {
        mockMvc.perform(get("/blog"))
                .andExpect(status().is3xxRedirection());
    }

}
