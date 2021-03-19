package webproject.watchshop.service;

import webproject.watchshop.model.view.OrderViewModel;

import java.io.IOException;
import java.util.List;

public interface OrderService {

    boolean saveOrders();

    List<OrderViewModel> getOrders(String loggedUser);

    boolean removeOrder(Long id) throws IOException;
}
