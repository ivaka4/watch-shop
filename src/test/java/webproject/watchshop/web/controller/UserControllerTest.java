package webproject.watchshop.web.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import webproject.watchshop.enums.RoleEnum;
import webproject.watchshop.model.entity.Address;
import webproject.watchshop.model.entity.Authority;
import webproject.watchshop.model.entity.User;
import webproject.watchshop.repository.AddressRepository;
import webproject.watchshop.repository.AuthorityRepository;
import webproject.watchshop.repository.UserRepository;


import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserControllerTest {

    private Long testUserId;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
//        this.init();
    }

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
    @WithMockUser(username = "pesho",authorities = {"USER", "ADMIN"})
    public void testPostProfilePage() throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "profilePicture",
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
                .param("phone", "624231").with(csrf()))
                .andExpect(status().is3xxRedirection()).andExpect(mvcResult -> {
                    "/users/profile".equals(mvcResult.getModelAndView().getViewName());
        });
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


//    private void init() {
//
//        Address address = new Address();
//        address.setCity("Vidin");
//        address.setAddress2("zhk Geo");
//        address.setAddress1("zhk Geo");
//        address.setPostCode("2412");
//        address.setCountry("Bulgaria");
//
//        Authority userRole = new Authority();
//        userRole.setAuthority(RoleEnum.USER);
//        Authority adminRole = new Authority();
//        adminRole.setAuthority(RoleEnum.ADMIN);
//
////        userRole = authorityRepository.save(userRole);
////        adminRole = authorityRepository.save(adminRole);
//
//
//        User userEntity = new User();
//        userEntity.setUsername("pesho");
//        userEntity.setPassword("xyz");
//        userEntity.setFirstName("petar");
//        userEntity.setLastName("petrov");
//        userEntity.setProfilePicture("https://upload.wikimedia.org/wikipedia/en/b/bd/Metallica_-_...And_Justice_for_All_cover.jpg");
//        userEntity.setEmail("abv@abv.bg");
//        userEntity.setAddress(address);
//        userEntity.setPhone(012522);
//        userEntity.setRegisterOn(LocalDateTime.now());
//        userEntity.setUpdatedOn(LocalDateTime.now());
//        userEntity.setAuthorities(Set.of(userRole,adminRole));
//        userEntity.setIsEnabled(true);
//        userEntity = userRepository.save(userEntity);
//
////        AlbumEntity albumEntity = new AlbumEntity();
////        albumEntity.
////                setName("JUSTICE FOR ALL").
////                setImageUrl("https://upload.wikimedia.org/wikipedia/en/b/bd/Metallica_-_...And_Justice_for_All_cover.jpg").
////                setVideoUrl("_fKAsvJrFes").
////                setDescription("Sample description").
////                setCopies(11).
////                setPrice(BigDecimal.TEN).
////                setReleaseDate(LocalDate.of(1988, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()).
////                setGenre(Genre.METAL).
////                setArtistEntity(artistEntity).
////                setUserEntity(userEntity);
////
////        albumEntity = albumRepository.save(albumEntity);
//        testUserId = userEntity.getId();
//    }


}
