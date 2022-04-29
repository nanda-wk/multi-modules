package com.teamyear.site.repository;

import com.teamyear.common.entity.Customer;
import com.teamyear.common.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String> {

    @Query("select o from Orders o where o.customer = ?1")
    List<Orders> findOrdersByCustomer(Customer customer);
}