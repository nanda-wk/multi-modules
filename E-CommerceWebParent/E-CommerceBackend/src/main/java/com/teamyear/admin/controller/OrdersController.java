package com.teamyear.admin.controller;

import com.teamyear.admin.service.OrdersService;
import com.teamyear.common.entity.OrderStatus;
import com.teamyear.common.entity.Orders;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/orders")
    public String ordersList(ModelMap model) {
        List<Orders> ordersList = ordersService.findAll();
        model.addAttribute("ordersList", ordersList);
        return "admin/";
    }

    @GetMapping("/order")
    public String updateOrderStatus(@RequestParam("id") String orderId,
                                    @RequestParam("status") String status) {
        Orders orders = ordersService.findById(orderId).get();
        orders.setOrderStatus(OrderStatus.valueOf(status));
        ordersService.save(orders);
        return "redirect:/orders";
    }

    @GetMapping("/order-detail")
    public String orderDetails(HttpServletRequest request, @RequestParam("id") String orderId, ModelMap model) {
        Orders orders = ordersService.findById(orderId).get();
        model.addAttribute("orderDetail", orders);
        return "admin/";
    }
}