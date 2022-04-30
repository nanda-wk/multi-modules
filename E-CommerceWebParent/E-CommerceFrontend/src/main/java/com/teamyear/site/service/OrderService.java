package com.teamyear.site.service;

import com.teamyear.common.entity.Customer;
import com.teamyear.common.entity.Orders;
import com.teamyear.site.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public <S extends Orders> S save(S entity) {
        return orderRepository.save(entity);
    }

    public Optional<Orders> findById(String s) {
        return orderRepository.findById(s);
    }

    @Query("select o from Orders o where o.customer = ?1")
    public List<Orders> findOrdersByCustomer(Customer customer) {
        return orderRepository.findOrdersByCustomer(customer);
    }

    public List<Orders> findAll() {
        return orderRepository.findAll();
    }

    @Query("select o from Orders o where o.customer = ?1 and o.orderId = ?2")
    public Orders findByCustomerAndOrderId(Customer customer, String orderId) {
        return orderRepository.findByCustomerAndOrderId(customer, orderId);
    }
}