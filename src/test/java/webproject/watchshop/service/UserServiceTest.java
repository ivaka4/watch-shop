package webproject.watchshop.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import webproject.watchshop.enums.RoleEnum;
import webproject.watchshop.model.entity.Authority;
import webproject.watchshop.model.entity.User;
import webproject.watchshop.repository.AddressRepository;
import webproject.watchshop.repository.AuthorityRepository;
import webproject.watchshop.repository.ProductRepository;
import webproject.watchshop.repository.UserRepository;
import webproject.watchshop.service.impl.UserServiceImpl;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserRepository mockUserRepository;

    private ModelMapper modelMapper;
    private AddressRepository addressRepository;
    private AuthorityService authorityService;
    private AuthorityRepository authorityRepository;
    private PasswordEncoder passwordEncoder;
    private ProductRepository productRepository;
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
        Assertions.assertEquals(2,userDetails.getAuthorities().size());

        Set<String> authorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Assertions.assertTrue(authorities.contains("ADMIN"));
        Assertions.assertTrue(authorities.contains("USER"));

    }
}



