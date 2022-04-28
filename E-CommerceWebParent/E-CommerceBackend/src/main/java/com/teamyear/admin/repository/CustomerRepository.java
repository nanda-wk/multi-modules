package com.teamyear.admin.repository;

import com.teamyear.common.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("select c from Customer c where c.username =?1")
    List<Customer> findByUsername(String username);

    @Query("select c from Customer c where c.email = ?1")
    List<Customer> findByEmail(String email);

}