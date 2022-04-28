package com.teamyear.admin.service;

import com.teamyear.admin.repository.CustomerRepository;
import com.teamyear.common.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Integer integer) {
        return customerRepository.findById(integer).get();
    }

    @Query("select c from Customer c where c.username =?1")
    public List<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Query("select c from Customer c where c.email = ?1")
    public List<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

}