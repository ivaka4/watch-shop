package webproject.watchshop.web.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import webproject.watchshop.model.view.OrderViewModel;
import webproject.watchshop.service.OrderService;
import webproject.watchshop.util.annotation.PageTitle;
import webproject.watchshop.util.Tools;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final Tools tools;

    public OrderController(OrderService orderService, ModelMapper modelMapper, Tools tools) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
        this.tools = tools;
    }

    @GetMapping("/finish")
    public ModelAndView finishOrder(){
        this.orderService.saveOrders();
        return super.redirect("/order/all");
    }

    @PageTitle(name = "Orders")
    @GetMapping("/all")
    public ModelAndView allOrders(){
        ModelAndView modelAndView = new ModelAndView("orders");
        List<OrderViewModel> orders = this.orderService.getOrders(this.tools.getLoggedUser());
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }

    @GetMapping("/remove/{id}")
    public ModelAndView removeOrder(@PathVariable Long id) throws IOException {
        this.orderService.removeOrder(id);
        return super.redirect("/order/all");
    }
}
