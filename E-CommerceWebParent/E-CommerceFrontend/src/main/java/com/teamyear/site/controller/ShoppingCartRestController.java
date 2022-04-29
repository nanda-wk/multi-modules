package com.teamyear.site.controller;

import com.teamyear.common.entity.Customer;
import com.teamyear.site.service.CartItemService;
import com.teamyear.site.util.CustomerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ShoppingCartRestController {

    @Autowired
    private CartItemService itemService;

    @GetMapping("/cart/add/{productId}/{quantity}")
    public String addProductToCart(@PathVariable("productId") Integer productId,
                                   @PathVariable("quantity") Integer quantity, HttpServletRequest request) {
        Customer customer = CustomerUtil.getAuthenticatedCustomer(request);
        Integer updatedQuantity;
        if (customer != null) {
            updatedQuantity = itemService.addProduct(productId, quantity, customer);
        }
        System.out.println("it work!");
        return "Item add to cart";
    }


}