package com.teamyear.admin.test.service;

import com.teamyear.admin.repository.ProductRepository;
import com.teamyear.admin.service.ProductService;
import com.teamyear.common.entity.Product;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductServieTest {

    @InjectMocks
    private ProductService service;

    @Mock
    private ProductRepository repository;

    private Product product() {
        Product p = new Product();
        p.setId(1);
        p.setProductName("Test Product Name");
        p.setProductDescritpion("Test Product Description");
        p.setPrice(99.9);
        p.setQuantity(8);
        p.setImg1("Test Product Img1");
        p.setImg2("Test Product Img2");
        p.setImg3("Test Product Img3");
        p.setImg4("Test Product Img4");
        p.setCreatedDate(new Date());
        p.setUpdatedDate(new Date());
        return p;
    }

    @Test
    void saveTest() {
        Product p = product();
        service.save(p);
        verify(repository, times(1)).save(p);
    }

    @Test
    void findAllTest() {
        service.findAll();
        verify(repository, times(1)).findAll();
    }

    @Test
    void findByIdTest() {
        service.findById(product().getId());
        verify(repository, times(1)).findById(product().getId());
    }

    @Test
    void deleteByIdTest() {
        service.deleteById(product().getId());
        verify(repository, times(1)).deleteById(product().getId());
    }

    @Test
    void countTest() {
        service.count();
        verify(repository, times(1)).count();
    }
}