package com.teamyear.site.controller;

import com.teamyear.common.entity.*;
import com.teamyear.site.formmodel.OrdersForm;
import com.teamyear.site.service.*;
import com.teamyear.site.util.CustomerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private CityService cityService;

    @Autowired
    private TownshipService townshipService;

    @Autowired
    private ProductService productService;

    @ModelAttribute("regionList")
    public List<Region> regionList() {
        return regionService.findAll();
    }

    @ModelAttribute("cityList")
    public List<City> cityList() {
        return cityService.findAll();
    }

    @ModelAttribute("townshipList")
    public List<Township> townshipList() {
        return townshipService.findAll();
    }

    @ModelAttribute("productList")
    public List<Product> productList() {
        return productService.findAll();
    }

    @GetMapping("/check-out")
    public String checkOut(HttpServletRequest request, ModelMap model) {
        Customer customer = CustomerUtil.getAuthenticatedCustomer(request);
        if (customer == null) {
            return "frontend/ECS-WEB003";
        }
        List<Orders> ordersList = orderService.findAll();
        OrdersForm orders = new OrdersForm();
        if (ordersList.size() == 0) {
            orders.setOrderId("ORDER#000000001");
        } else {
            String orderId = String.format("ORDER#%09d", ordersList.size() + 1);
            orders.setOrderId(orderId);
        }

        model.addAttribute("customer", customer);
        model.addAttribute("form", orders);
        return "frontend/ECS-CHK001";
    }

    @PostMapping("/place-order")
    public String placeOrder(HttpServletRequest request, @ModelAttribute("form") OrdersForm form,
                             @RequestParam(name = "productIds") Integer[] productIds,
                             @RequestParam(name = "quantities") Integer[] quantities) {
        Customer customer = CustomerUtil.getAuthenticatedCustomer(request);

//        List<Integer> productIds = new ArrayList<>();
//        productIds.add(Integer.valueOf(request.getParameter("productId")));
//        List<Integer> quantities = new ArrayList<>();
//        quantities.add(Integer.valueOf(request.getParameter("quantities")));


        System.out.println("This is quantities size ::: " + productIds.length);
        System.out.println("This is productIds size ::: " + quantities.length);

        Orders orders = new Orders();
        Product product;

        List<Product> productList = new ArrayList<>();

        orders.setOrderId(form.getOrderId());
        orders.setCustomer(customer);
        orders.setPaymentMethod(form.getPaymentMethod());
        orders.setTotal(Double.valueOf(request.getParameter("total")));
//        for (Integer productId : productIds) {
//            for (Integer quantity : quantities) {
//                product = productService.findById(productId);
//                product.setQuantity(product.getQuantity() - quantity);
//                productService.save(product);
//                orders.adOrderDetails(quantity, (product.discountedPrice() * quantity), product);
//            }
//        }
        for (Integer productId : productIds) {
            productList.add(productService.findById(productId));
        }
        for (Integer quantity : quantities) {
            for (Product p : productList) {
                p.setQuantity(p.getQuantity() - quantity);
                productService.save(p);
                orders.adOrderDetails(quantity, (p.discountedPrice() * quantity), p);
            }
        }
        orderService.save(orders);
        return "frontend/WEB-003";
    }

}