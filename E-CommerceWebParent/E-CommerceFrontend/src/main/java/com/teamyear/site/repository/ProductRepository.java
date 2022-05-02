package com.teamyear.site.repository;

import com.teamyear.common.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from product limit 4", nativeQuery = true)
    List<Product> findByThreeRow();

}