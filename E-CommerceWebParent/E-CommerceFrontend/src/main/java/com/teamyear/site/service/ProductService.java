package com.teamyear.site.service;

import com.teamyear.common.entity.Product;
import com.teamyear.site.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Query(value = "select * from product limit 4", nativeQuery = true)
    public List<Product> findByThreeRow() {
        return productRepository.findByThreeRow();
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setCreatedDate(new Date());
        }
        product.setUpdatedDate(new Date());
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Integer id) {
        return productRepository.findById(id).get();
    }

    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }


}