package com.book_store.controller.customer_controller;

import com.book_store.entity.Order;
import com.book_store.entity.OrderDetail;
import com.book_store.entity.Product;
import com.book_store.entity.User;
import com.book_store.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class OrderCustomerController {
    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartService cartService;

    @GetMapping("/myorder")
    public String listorder(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        User user = userService.findByUsername(authentication.getName());
        int customerId= user.getCustomer().getId();
        model.addAttribute("user", user);
        model.addAttribute("orderlist",orderService.getAllUserOrder(customerId));
        model.addAttribute("size_carts", cartService.getCartSize());
        return "user_order";
    }

    @RequestMapping("/myorder/{id}")
    public String vieworderdetails(@PathVariable("id") Integer id, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        if (!(authentication == null || authentication instanceof AnonymousAuthenticationToken)) {
            user = userService.findByUsername(authentication.getName());
        }
        model.addAttribute("orderdetail",orderDetailService.getOrderDetailsByOrderId(id));
        model.addAttribute("order",orderService.findOrderbyOrderId(id));
        model.addAttribute("size_carts", cartService.getCartSize());
        model.addAttribute("user", user);
        return "order_detail";
    }

    @RequestMapping("/myorder/cancel/{id}")
    public String CancelOrders(@PathVariable("id") Integer id, Model model){
        List<OrderDetail> orderDetails= orderDetailService.getOrderDetailsByOrderId(id);
        for(OrderDetail orderDetail:orderDetails){
            Product product= productService.getProduct(orderDetail.getProduct().getId());
            product.setQuantity(product.getQuantity()+orderDetail.getQuantity());
        }
        Order order= orderService.findOrderbyOrderId(id);
        order.setStatus(3);
        orderService.saveOrder(order);
        return "redirect:/myorder";
    }
}
