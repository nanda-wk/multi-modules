package com.teamyear.admin.controller;

import com.teamyear.admin.service.OrdersService;
import com.teamyear.common.entity.OrderStatus;
import com.teamyear.common.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        return "admin/ECS-ORD001";
    }

    @PostMapping("/order")
    public String updateOrderStatus(@RequestParam(name = "oId") String orderId,
                                    @RequestParam(name = "status") String status) {
        Orders orders = ordersService.findById(orderId).get();
        orders.setOrderStatus(OrderStatus.valueOf(status));
        ordersService.save(orders);
        return "redirect:/admin/orders";
    }

    @GetMapping("/order-detail")
    public String orderDetails(@RequestParam("id") String orderId, ModelMap model) {
        Orders orders = ordersService.findById(orderId).get();
        model.addAttribute("detail", orders);
        return "admin/ECS-ORD002";
    }
}