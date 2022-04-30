package com.teamyear.admin.service;

import com.teamyear.admin.repository.OrdersRepository;
import com.teamyear.common.entity.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }

    public <S extends Orders> S save(S entity) {
        return ordersRepository.save(entity);
    }

    public Optional<Orders> findById(String s) {
        return ordersRepository.findById(s);
    }

    public long count() {
        return ordersRepository.count();
    }
}