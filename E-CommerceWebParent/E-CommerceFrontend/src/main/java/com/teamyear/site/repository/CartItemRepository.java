package com.teamyear.site.repository;

import com.teamyear.common.entity.CartItem;
import com.teamyear.common.entity.Customer;
import com.teamyear.common.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    @Query("select c from CartItem c where c.customer = ?1")
    public List<CartItem> findByCustomer(Customer customer);

    @Query("select c from CartItem c where c.customer = ?1 and c.product = ?2")
    public CartItem findByCustomerAndProduct(Customer customer, Product product);

    @Modifying
    @Query("update CartItem c set c.quantity = ?1 where c.customer.id = ?2 and c.product.id = ?3")
    public void updateQuantity(Integer quantity, Integer customerId, Integer productId);

    @Modifying
    @Query("delete from CartItem c where c.customer.id = ?1 and c.product.id = ?2")
    public void deleteByCustomerAndProduct(Integer customerId, Integer productId);

    @Modifying
    @Query("delete from CartItem c where c.customer.id = ?1")
    public void deleteByCustomer(Integer customerId);
}