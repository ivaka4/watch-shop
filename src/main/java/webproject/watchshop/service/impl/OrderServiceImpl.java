package webproject.watchshop.service.impl;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webproject.watchshop.model.entity.Order;
import webproject.watchshop.model.entity.Product;
import webproject.watchshop.model.entity.User;
import webproject.watchshop.model.view.OrderViewModel;
import webproject.watchshop.repository.OrderRepository;
import webproject.watchshop.repository.UserRepository;
import webproject.watchshop.service.OrderService;
import webproject.watchshop.service.ProductService;
import webproject.watchshop.util.Tools;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final Tools tools;
    private final ProductService productService;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper, UserRepository userRepository, Tools tools, ProductService productService) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.tools = tools;
        this.productService = productService;
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
                order.setProductName(product.getName());
                order.setProductNumber(product.getProductNumber());
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
    public List<OrderViewModel> getOrders(String loggedUser) {
        User user = this.userRepository.findUserByUsername(loggedUser).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("No orders with this account");
        }
        List<Order> orders = this.orderRepository.findAllByUserId(user.getId());
        return this.modelMapper.map(orders, new TypeToken<List<OrderViewModel>>(){}.getType());
    }
}
