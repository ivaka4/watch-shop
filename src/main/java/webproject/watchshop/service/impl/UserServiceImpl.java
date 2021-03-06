package webproject.watchshop.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import webproject.watchshop.enums.RoleEnum;
import webproject.watchshop.exceptions.addressEx.AddressIsNotExistException;
import webproject.watchshop.exceptions.productEx.ProductIdNotValid;
import webproject.watchshop.exceptions.userEx.UserCannotSaveException;
import webproject.watchshop.exceptions.userEx.UserRegistrationException;
import webproject.watchshop.model.entity.Address;
import webproject.watchshop.model.entity.Product;
import webproject.watchshop.model.entity.User;
import webproject.watchshop.model.entity.UserSecurity;
import webproject.watchshop.model.service.ProductServiceModel;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.model.view.AddressViewModel;
import webproject.watchshop.model.view.UserViewModel;
import webproject.watchshop.repository.AddressRepository;
import webproject.watchshop.repository.AuthorityRepository;
import webproject.watchshop.repository.ProductRepository;
import webproject.watchshop.repository.UserRepository;
import webproject.watchshop.service.AuthorityService;
import webproject.watchshop.service.CloudinaryService;
import webproject.watchshop.service.UserService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;
    private final AuthorityService authorityService;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;
    private final CloudinaryService cloudinaryService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, AddressRepository addressRepository, AuthorityService authorityService, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, ProductRepository productRepository, CloudinaryService cloudinaryService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
        this.authorityService = authorityService;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.productRepository = productRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    @Transactional
    public UserServiceModel register(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        if (user == null) {
            throw new UserCannotSaveException("Not valid user");
        }
        addAddressToUser(userServiceModel, user);
        user.setRegisterOn(LocalDateTime.now());
        user.setUpdatedOn(LocalDateTime.now());
        user.setIsEnabled(true);
        //default profile picture
        user.setProfilePicture("https://cdn.vox-cdn.com/thumbor/qds1ovjTYIqLY6Cr2jW1YfeDJ-s=/1400x1400/filters:format(jpeg)/cdn.vox-cdn.com/uploads/chorus_asset/file/21730397/avatar_airbender.jpg");
        if (userRepository.count() == 0) {
            authorityService.seedAuthorities();
            user.setAuthorities(new HashSet<>(authorityRepository.findAll()));
        } else {
            user.setAuthorities(new HashSet<>(authorityRepository.findAllByAuthority(RoleEnum.USER)));
        }
        user.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        try {
            this.userRepository.saveAndFlush(user);
        } catch (Exception e) {
            throw new UserRegistrationException("Oops something went wrong, user cannot be saved! Try again");
        }
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    private void addAddressToUser(UserServiceModel userServiceModel, User user) {
        AddressViewModel addressViewModel = new AddressViewModel();
        addressViewModel.setCity(userServiceModel.getCity());
        addressViewModel.setCountry(userServiceModel.getCountry());
        addressViewModel.setPostCode(userServiceModel.getPostCode());
        addressViewModel.setAddress1(userServiceModel.getAddress1());
        addressViewModel.setAddress2(userServiceModel.getAddress2());
        user.setAddress(this.modelMapper.map(addressViewModel, Address.class));
    }

    @Override
    public boolean emailExist(String email) {
        return userRepository.findUserByEmail(email) == null;
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    @Transactional
    public UserServiceModel findByUsername(String loggedUser) {
        User user = this.userRepository.findByUsername(loggedUser);
        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        return userServiceModel;
    }

    @Override
    @Transactional
    public UserServiceModel updateProfile(UserServiceModel userServiceModel) throws IOException {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername());
        user.setUpdatedOn(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        MultipartFile multipartFile = userServiceModel.getPicture();
        String imgUrl = this.cloudinaryService.uploadImage(multipartFile);
        user.setProfilePicture(imgUrl);
        user.setFirstName(userServiceModel.getFirstName());
        user.setLastName(userServiceModel.getLastName());
        user.setPhone(Integer.parseInt(userServiceModel.getPhone()));
        user.setEmail(userServiceModel.getEmail());
        this.userRepository.saveAndFlush(user);
        Address userAddress = user.getAddress();
        if (userAddress != null) {
            userAddress.setCountry(userServiceModel.getCountry());
            userAddress.setCity(userServiceModel.getCity());
            userAddress.setPostCode(userServiceModel.getPostCode());
            userAddress.setAddress1(userServiceModel.getAddress1());
            userAddress.setAddress2(userServiceModel.getAddress2());
            this.addressRepository.saveAndFlush(userAddress);
            user.setAddress(userAddress);
        } else {
            throw new AddressIsNotExistException("Address is not Exist (internal error)!");
        }
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    @Transactional
    public List<UserViewModel> getAllUsers() {
        return this.modelMapper.map(this.userRepository.findAll(), new TypeToken<List<UserViewModel>>() {
        }.getType());
    }

    @Override
    @Transactional
    public UserServiceModel changeRole(String username, RoleEnum authority){
        User user = this.userRepository.findUserByUsername(username).orElse(null);
        if (user != null) {
            if (user.getAuthorities().size() == 2 && authority.equals(RoleEnum.USER)) {
                user.getAuthorities().clear();
                user.setAuthorities(new HashSet<>());
                user.getAuthorities().add(authorityRepository.findByAuthority(authority));
                userRepository.saveAndFlush(user);

            } else if (authority.equals(RoleEnum.ADMIN)) {
                user.getAuthorities().add(authorityRepository.findByAuthority(authority));
                userRepository.saveAndFlush(user);
            }
        } else {
            throw new UserCannotSaveException("User cannot be saved");
        }
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    @Transactional
    public boolean addToCart(String username,ProductServiceModel productServiceModel) throws UserCannotSaveException {
        Product product = this.productRepository.findById(productServiceModel.getId()).orElse(null);
        User user = this.userRepository.findUserByUsername(username).orElse(null);
        if (user == null || product == null || user.getCart().contains(product)){
            throw new UserCannotSaveException("Cannot add product to cart! Already in cart");
        }
        user.getCart().add(product);
        this.userRepository.saveAndFlush(user);
        return true;
    }

    @Override
    @Transactional
    public boolean removeAllProducts(String loggedUser) {
        User user = this.userRepository.findUserByUsername(loggedUser).orElse(null);
        if (user == null){
            throw new UsernameNotFoundException("Cannot find user to clear the cart");
        }
        user.getCart().removeAll(user.getCart());
        this.userRepository.saveAndFlush(user);
        return true;
    }

    @Override
    @Transactional
    public boolean removeSingleProduct(Long id, String loggedUser) {
        User user = this.userRepository.findUserByUsername(loggedUser).orElse(null);
        if (user == null){
            throw new UsernameNotFoundException("User cannot be found");
        }
        Product product = this.productRepository.findById(id).orElse(null);
        if (product == null){
            throw new ProductIdNotValid("Product with this id was not found");
        }
        user.getCart().remove(product);
        this.userRepository.saveAndFlush(user);
        return true;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findUserByUsername(s);
        user.orElseThrow(() -> new UsernameNotFoundException(s));
        return user.map(UserSecurity::new).get();
    }
}
