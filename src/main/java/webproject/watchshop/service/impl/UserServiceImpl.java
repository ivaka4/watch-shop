package webproject.watchshop.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webproject.watchshop.enums.RoleEnum;
import webproject.watchshop.model.entity.Address;
import webproject.watchshop.model.entity.User;
import webproject.watchshop.model.entity.UserSecurity;
import webproject.watchshop.model.service.UserServiceModel;
import webproject.watchshop.model.view.AddressViewModel;
import webproject.watchshop.model.view.UserViewModel;
import webproject.watchshop.repository.AddressRepository;
import webproject.watchshop.repository.AuthorityRepository;
import webproject.watchshop.repository.UserRepository;
import webproject.watchshop.service.AuthorityService;
import webproject.watchshop.service.UserService;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;
    private final AuthorityService authorityService;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, AddressRepository addressRepository, AuthorityService authorityService, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.addressRepository = addressRepository;
        this.authorityService = authorityService;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserServiceModel register(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        addAddressToUser(userServiceModel, user);
        user.setRegisterOn(LocalDateTime.now());
        user.setUpdatedOn(LocalDateTime.now());
        if (userRepository.count() == 0){
            authorityService.seedAuthorities();
            user.setAuthorities(new HashSet<>(authorityRepository.findAll()));
        } else {
            user.setAuthorities(new HashSet<>(authorityRepository.findAllByAuthority(RoleEnum.USER)));
        }
        user.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));

        this.userRepository.saveAndFlush(user);
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
        System.out.println();
        UserServiceModel userServiceModel = this.modelMapper.map(user, UserServiceModel.class);
        System.out.println();
        return userServiceModel;
    }

    @Override
    @Transactional
    public UserServiceModel updateProfile(UserServiceModel userServiceModel) {
        User user = this.userRepository.findByUsername(userServiceModel.getUsername());
        user.setUpdatedOn(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(userServiceModel.getPassword()));
        addAddressToUser(userServiceModel, user);
        user.setProfilePicture(userServiceModel.getProfilePicture());
        user.setFirstName(userServiceModel.getFirstName());
        user.setLastName(userServiceModel.getLastName());
        user.setPhone(Integer.parseInt(userServiceModel.getPhone()));
        user.setEmail(userServiceModel.getEmail());
        return null;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = this.userRepository.findUserByUsername(s);
        user.orElseThrow(() -> new UsernameNotFoundException(s));
        return user.map(UserSecurity::new).get();
    }
}
