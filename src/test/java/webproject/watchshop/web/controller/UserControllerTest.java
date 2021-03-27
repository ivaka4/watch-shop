package webproject.watchshop.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.With;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import webproject.watchshop.WatchShopApplication;
import webproject.watchshop.enums.RoleEnum;
import webproject.watchshop.model.entity.Authority;
import webproject.watchshop.model.entity.User;
import webproject.watchshop.model.entity.UserSecurity;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.repository.AuthorityRepository;
import webproject.watchshop.repository.ProductRepository;
import webproject.watchshop.repository.UserRepository;
import webproject.watchshop.service.UserService;


import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = WatchShopApplication.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthorityRepository authorityRepository;

    @MockBean
    private UserService mockUserService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testGetUserLoginPage() throws Exception {
        mockMvc.perform(get("/users/login")).andExpect(status().isOk());
    }


    @Test
    public void testGetUserRegisterPage() throws Exception {
        mockMvc.perform(get("/users/register")).andExpect(status().isOk());
    }

    @Test
    public void testPostUserRegisterPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/register")
                .param("username", "TestName")
                .param("password", "123123Test@")
                .param("confirmPassword", "123123Test@")
                .param("firstName", "Test")
                .param("lastName", "Model")
                .param("email", "test@mail.bg")
                .param("country", "Bulgaria")
                .param("city", "vidin")
                .param("postCode", "3252")
                .param("address1", "zhk Geo")
                .param("address2", "zhk Geo")
                .param("phone", "624231")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(mvcResult -> {
                    "/users/login".equals(mvcResult.getModelAndView().getViewName());
                });
    }
    @Test
    public void testPostUserRegisterPageWithWrongPassword() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/register")
                .param("username", "TestName")
                .param("password", "123123Test@")
                .param("confirmPassword", "123123Test")
                .param("firstName", "Test")
                .param("lastName", "Model")
                .param("email", "test@mail.bg")
                .param("country", "Bulgaria")
                .param("city", "vidin")
                .param("postCode", "3252")
                .param("address1", "zhk Geo")
                .param("address2", "zhk Geo")
                .param("phone", "624231")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(mvcResult -> {
                    "/users/register".equals(mvcResult.getModelAndView().getViewName());
                });
    }

    @Test
    @WithMockUser(username = "TestName",authorities = {"USER", "ADMIN"})
    public void testPostProfilePage() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "photos",
                "file.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "This is the file content".getBytes());

        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/users/update")
                .file(mockMultipartFile)
                .param("password", "123123Test@")
                .param("confirmPassword", "123123Test@")
                .param("firstName", "Test")
                .param("lastName", "Model")
                .param("email", "test1@mail.bg")
                .param("country", "Bulgaria")
                .param("city", "vidin")
                .param("postCode", "3252")
                .param("address1", "zhk Geo")
                .param("address2", "zhk Geo")
                .param("phone", "624231"))
                .andExpect(status().is3xxRedirection());
    }
    @Test
    public void testUserProfilePageWithoutLogIn() throws Exception {
      mockMvc.perform(get("/users/profile"))
              .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testUserRolesWithoutLoggedIn() throws Exception {
      mockMvc.perform(get("/users/roles/add"))
              .andExpect(status().is3xxRedirection());
    }


    @Test
    @WithMockUser(username = "TestName",authorities = {"USER"})
    public void testUserRolesWithoutAdminAccess() throws Exception {
      mockMvc.perform(get("/users/roles/add"))
              .andExpect(status().is4xxClientError());
    }


}
