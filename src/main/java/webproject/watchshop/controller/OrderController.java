package webproject.watchshop.controller;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import webproject.watchshop.model.view.OrderViewModel;
import webproject.watchshop.service.OrderService;
import webproject.watchshop.util.Tools;

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
        return super.redirect("/orders/all");
    }

    @GetMapping("/all")
    public ModelAndView allOrders(){
        ModelAndView modelAndView = new ModelAndView("orders");
        List<OrderViewModel> orders = this.orderService.getOrders(this.tools.getLoggedUser());
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }
}
