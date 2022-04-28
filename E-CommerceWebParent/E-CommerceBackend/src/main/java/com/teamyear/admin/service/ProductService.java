package com.teamyear.admin.service;

import com.teamyear.admin.repository.ProductRepository;
import com.teamyear.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

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