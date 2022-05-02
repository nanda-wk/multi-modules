package com.teamyear.admin.test.service;

import com.teamyear.admin.repository.BrandRepository;
import com.teamyear.admin.service.BrandService;
import com.teamyear.common.entity.Brand;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;


import static org.mockito.Mockito.*;
@SpringBootTest
public class BrandServiceTest {

    @InjectMocks
    private BrandService service;

    @Mock
    private BrandRepository repository;

    private Brand createBrand() {
        Brand brand = new Brand();
        brand.setId(1);
        brand.setBrandName("Test Brand Name");
        brand.setBrandDescription("Test brand Description");
        brand.setBrandLogo("Test Brand Image Path/TestBrandImg.jpg");
        return  brand;
    }

    @Test
    void saveTest() {
        Brand b = createBrand();
        service.save(b);
        verify(repository, times(1)).save(b);
    }

    @Test
    void findALlTest() {
        Sort sortName = Sort.by("brandName").ascending();
        service.findAll();
        verify(repository, times(1)).findAll(sortName);
    }

    @Test
    void findByIdTest() {
        Brand b = createBrand();
        service.findById(b.getId());
        verify(repository, times(1)).findById(b.getId());
    }

    @Test
    void findByBrandNameTest() {
        Brand b = createBrand();
        service.findByBrandName(b.getBrandName());
        verify(repository, times(1)).findByBrandName(b.getBrandName());
    }

    @Test
    void deleteByIdTest() {
        Brand b = createBrand();
        service.deleteById(b.getId());
        verify(repository, times(1)).deleteById(b.getId());
    }
}