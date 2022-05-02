package com.teamyear.admin.test.service;

import com.teamyear.admin.repository.DiscountRepository;
import com.teamyear.admin.service.DiscountService;
import com.teamyear.common.entity.Discount;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.*;

@SpringBootTest
public class DiscountServiceTest {

    @InjectMocks
    private DiscountService service;

    @Mock
    private DiscountRepository repository;

    private Discount discount() {
        Discount d = new Discount();
        d.setId(1);
        d.setDiscountName("Test Discount Name");
        d.setDiscountPercent(20);
        return d;
    }

    @Test
    void saveTest() {
        Discount d = discount();
        service.save(d);
        verify(repository, times(1)).save(d);
    }

    @Test
    void findALlTest() {
        Discount d = discount();
        Sort sort = Sort.by("discountName").ascending();
        service.findAll();
        verify(repository, times(1)).findAll(sort);
    }

    @Test
    void findByDiscountNameTest() {
        Discount d = discount();
        service.findByDiscountName(d.getDiscountName());
        verify(repository, times(1)).findByDiscountName(d.getDiscountName());
    }

    @Test
    void findByIdTest() {
        Discount d = discount();
        service.findById(d.getId());
        verify(repository, times(1)).findById(d.getId());
    }

    @Test
    void deleteById() {
        service.deleteById(discount().getId());
        verify(repository, times(1)).deleteById(discount().getId());
    }
}