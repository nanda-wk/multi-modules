package com.teamyear.site.service;

import com.teamyear.common.entity.CartItem;
import com.teamyear.common.entity.Customer;
import com.teamyear.common.entity.Product;
import com.teamyear.site.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    @Autowired
    private CartItemRepository itemRepository;

    @Autowired
    private ProductService productService;

    public Integer addProduct(Integer productId, Integer quantity, Customer customer) {
        Integer updatedQuantity = quantity;
        Product product = productService.findById(productId);
        CartItem cartItem = itemRepository.findByCustomerAndProduct(customer, product);

        if (cartItem != null) {
            updatedQuantity = cartItem.getQuantity() + quantity;
        } else {
            cartItem = new CartItem();
            cartItem.setCustomer(customer);
            cartItem.setProduct(product);
        }
        cartItem.setQuantity(updatedQuantity);
        itemRepository.save(cartItem);
        return updatedQuantity;
    }

    public List<CartItem> listCartItems(Customer customer) {
        return itemRepository.findByCustomer(customer);
    }

    public double updateQuantity(Integer productId, Integer quantity, Customer customer) {
        itemRepository.updateQuantity(quantity, customer.getId(), productId);

        Product product = productService.findById(productId);

        return product.discountedPrice() * quantity;
    }

    public void removeProduct(Integer productId, Customer customer) {
        itemRepository.deleteByCustomerAndProduct(customer.getId(), productId);
    }

    public void deleteByCustomer(Customer customer) {
        itemRepository.deleteByCustomer(customer.getId());
    }
}