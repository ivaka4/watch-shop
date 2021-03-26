package webproject.watchshop.web.controller;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import webproject.watchshop.WatchShopApplication;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.service.UserService;

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

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService mockUserService;

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
    @WithMockUser(username=TEST_USER,authorities={"USER", "ADMIN"})
    public void getAddProductPage() throws Exception {
        when(mockUserService.findByUsername(TEST_USER)).thenReturn(
                new UserServiceModel()//todo - maybe add some props here
        );

        mockMvc.perform(MockMvcRequestBuilders.get("/product/add)").with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"USER", "ADMIN"})
    public void getAddProductPageWhenNotLogged() throws Exception {
        when(mockUserService.findByUsername(TEST_USER)).thenReturn(
                new UserServiceModel()//todo - maybe add some props here
        );

        mockMvc.perform(MockMvcRequestBuilders
                .get("/product/add"))
                .andExpect(status().is2xxSuccessful());
    }

}
