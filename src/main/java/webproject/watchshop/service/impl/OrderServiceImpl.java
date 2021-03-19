package webproject.watchshop.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webproject.watchshop.exceptions.orderEx.OrderIdNotValid;
import webproject.watchshop.model.entity.*;
import webproject.watchshop.model.view.OrderViewModel;
import webproject.watchshop.repository.OrderRepository;
import webproject.watchshop.repository.ProductRepository;
import webproject.watchshop.repository.UserRepository;
import webproject.watchshop.service.OrderService;
import webproject.watchshop.service.ProductCategoryService;
import webproject.watchshop.service.ProductService;
import webproject.watchshop.util.Tools;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final Tools tools;
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ProductCategoryService productCategoryService;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, UserRepository userRepository, Tools tools, ProductRepository productRepository, ProductService productService, ProductCategoryService productCategoryService) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.tools = tools;
        this.productRepository = productRepository;
        this.productService = productService;

        this.productCategoryService = productCategoryService;
    }

    @Override
    @Transactional
    public boolean saveOrders() {
        User user = this.userRepository.findUserByUsername(this.tools.getLoggedUser()).orElse(null);
        if (user != null && user.getCart().size() > 0){
            for (Product product : user.getCart()) {
                Order order = new Order();
                order.setBuyDate(LocalDateTime.now());
                order.setFirstName(user.getFirstName());
                order.setLastName(user.getLastName());
                order.setPrice(product.getPrice());
                order.setProductDescription(product.getDescription());
                order.setProductModel(product.getModel());
                order.setProductMake(product.getMake());
                order.setProductName(product.getName());
                order.setProductNumber(product.getProductNumber());
                order.setProductCategory(product.getCategory().getCategory());
                order.setProductId(product.getId());
                order.setOrderImages(product.getImageUrls());
                order.setUser(user);
                this.orderRepository.save(order);
                this.productService.removeProduct(product.getId());
            }
            user.getCart().clear();
            this.userRepository.saveAndFlush(user);

        } else {
            throw new UsernameNotFoundException("Cannot make order without user");
        }
        return true;
    }

    @Override
    @Transactional
    public List<OrderViewModel> getOrders(String loggedUser) {
        User user = this.userRepository.findUserByUsername(loggedUser).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("No orders with this account");
        }
        List<Order> orders = this.orderRepository.findAllByUserId(user.getId());
        return this.modelMapper.map(orders, new TypeToken<List<OrderViewModel>>(){}.getType());
    }

    @Override
    public boolean removeOrder(Long id) throws IOException {
        Order order = this.orderRepository.findById(id).orElse(null);
        if (order == null){
            throw new OrderIdNotValid("Cannot remove order with this id");
        }
        Product productServiceModel = new Product();
        productServiceModel.setId(order.getProductId());
        productServiceModel.setName(order.getProductName());
      this.productCategoryService.findProductCategory(order.getProductCategory());
        productServiceModel.setCategory(
                this.modelMapper
                        .map(this.productCategoryService.findProductCategory(order.getProductCategory()), ProductCategory.class));
        productServiceModel.setImageUrls(order.getOrderImages());
        productServiceModel.setDescription(order.getProductDescription());
        productServiceModel.setMake(order.getProductMake());
        productServiceModel.setModel(order.getProductModel());
        productServiceModel.setProductNumber(order.getProductNumber());
        productServiceModel.setPrice(order.getPrice());
        this.productRepository.saveAndFlush(productServiceModel);
        this.orderRepository.delete(order);
        return true;
    }
}
