package webproject.watchshop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.multipart.MultipartFile;
import webproject.watchshop.enums.RoleEnum;
import webproject.watchshop.model.entity.Address;
import webproject.watchshop.model.entity.Authority;
import webproject.watchshop.model.entity.User;
import webproject.watchshop.model.service.AuthorityServiceModel;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.model.view.AddressViewModel;
import webproject.watchshop.repository.AddressRepository;
import webproject.watchshop.repository.AuthorityRepository;
import webproject.watchshop.repository.ProductRepository;
import webproject.watchshop.repository.UserRepository;
import webproject.watchshop.service.impl.UserServiceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@DirtiesContext
public class UserServiceTest {

    private Long testUserId;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository mockUserRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AuthorityService authorityService;
    @Mock
    private AuthorityRepository authorityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(mockUserRepository,
                modelMapper,
                addressRepository,
                authorityService,
                authorityRepository,
                passwordEncoder,
                productRepository,
                cloudinaryService);
//        this.init();
    }


    @Test
    public void testUserNotFound() {
        Assertions.assertThrows(
                UsernameNotFoundException.class, () -> {
                    userService.loadUserByUsername("user_does_not_exists");
                }
        );
    }

    @Test
    public void testExistingUser() {
        User userEntity = new User();
        userEntity.setUsername("ivaka4");
        userEntity.setPassword("xyz");
        Authority userRole = new Authority();
        userRole.setAuthority(RoleEnum.USER);
        Authority adminRole = new Authority();
        adminRole.setAuthority(RoleEnum.ADMIN);

        userEntity.setAuthorities(Set.of(userRole, adminRole));

        Mockito.when(mockUserRepository.findUserByUsername("ivaka4"))
                .thenReturn(Optional.of(userEntity));

        UserDetails userDetails = userService.loadUserByUsername("ivaka4");

        Assertions.assertEquals(userEntity.getUsername(), userDetails.getUsername());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());

        Set<String> authorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Assertions.assertTrue(authorities.contains("ADMIN"));
        Assertions.assertTrue(authorities.contains("USER"));
    }

    @Test
    @DirtiesContext
    public void testUserRegisterWithValidInput() throws IOException {
        AddressViewModel address = new AddressViewModel();
        address.setCity("Vidin");
        address.setAddress2("zhk Geo");
        address.setAddress1("zhk Geo");
        address.setPostCode("2412");
        address.setCountry("Bulgaria");

//        Authority userRole = new Authority();
//        userRole.setAuthority(RoleEnum.USER);
//        Authority adminRole = new Authority();
//        adminRole.setAuthority(RoleEnum.ADMIN);
//        authorityRepository.save(userRole);
//        authorityRepository.save(adminRole);



//        userRole = authorityRepository.save(userRole);
//        adminRole = authorityRepository.save(adminRole);


        UserServiceModel userEntity = new UserServiceModel();
        userEntity.setUsername("pesho");
        userEntity.setPassword("xyz");
        userEntity.setConfirmPassword("xyz");
        userEntity.setFirstName("petar");
        userEntity.setLastName("petrov");
        userEntity.setProfilePicture("https://upload.wikimedia.org/wikipedia/en/b/bd/Metallica_-_...And_Justice_for_All_cover.jpg");
        userEntity.setEmail("abv@abv.bg");
        userEntity.setAddress1(address.getAddress1());
        userEntity.setAddress2(address.getAddress2());
        userEntity.setPostCode(address.getPostCode());
        userEntity.setCountry(address.getCountry());
        userEntity.setCity(address.getCity());
        userEntity.setAddress(address);
        userEntity.setPhone("012522");
        File file = new File("C:\\Users\\Ivailo.DESKTOP-J380DFT\\Desktop\\IMG_20200408_212850.jpg");
        FileInputStream image1 = new FileInputStream(file);
        MultipartFile[] multipartFiles = new MultipartFile[3];
        multipartFiles[0] = new MockMultipartFile("file",
                file.getName(), "image/jpg", image1.readAllBytes());
        userEntity.setPicture(multipartFiles[0]);
//        userEntity.setAuthorities(Set.
//                of(this.modelMapper.map(userRole, AuthorityServiceModel.class), this.modelMapper.map(adminRole, AuthorityServiceModel.class)));
        System.out.println();
        UserServiceModel result = userService.register(userEntity);
        System.out.println();
        Assertions.assertEquals(userEntity.getUsername(), result.getUsername());



    }

    private void init() {
//
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
        userEntity = mockUserRepository.save(userEntity);

//        AlbumEntity albumEntity = new AlbumEntity();
//        albumEntity.
//                setName("JUSTICE FOR ALL").
//                setImageUrl("https://upload.wikimedia.org/wikipedia/en/b/bd/Metallica_-_...And_Justice_for_All_cover.jpg").
//                setVideoUrl("_fKAsvJrFes").
//                setDescription("Sample description").
//                setCopies(11).
//                setPrice(BigDecimal.TEN).
//                setReleaseDate(LocalDate.of(1988, 3, 3).atStartOfDay(ZoneId.systemDefault()).toInstant()).
//                setGenre(Genre.METAL).
//                setArtistEntity(artistEntity).
//                setUserEntity(userEntity);
//
//        albumEntity = albumRepository.save(albumEntity);
        testUserId = userEntity.getId();
        System.out.println();
    }
}



