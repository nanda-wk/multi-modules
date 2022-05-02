package com.teamyear.admin.test.service;

import com.teamyear.admin.repository.CategoryRepository;
import com.teamyear.admin.service.CategoryService;
import com.teamyear.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.*;

@SpringBootTest
public class CategoryServiceTest {

    @InjectMocks
    private CategoryService service;

    @Mock
    private CategoryRepository repository;

    private Category category() {
        Category c = new Category();
        c.setId(1);
        c.setCategoryName("Test Category Name");
        c.setCategoryDescription("Test Category Description");
        return c;
    }

    @Test
    void deleteByIdTest() {
        Category c = category();
        service.deleteById(c.getId());
        verify(repository, times(1)).deleteById(c.getId());
    }

    @Test
    void findAllTest() {
        Category c = category();
        Sort sort = Sort.by("categoryName").ascending();
        service.findAll();
        verify(repository, times(1)).findAll(sort);
    }

    @Test
    void findByCategoryNameTest() {
        Category c = category();
        service.findByCategoryName(c.getCategoryName());
        verify(repository, times(1)).findByCategoryName(c.getCategoryName());
    }

    @Test
    void findByIdTest() {
        Category c = category();
        service.findById(c.getId());
        verify(repository, times(1)).findById(c.getId());
    }

    @Test
    void saveTest() {
        Category c = category();
        service.save(c);
        verify(repository, times(1)).save(c);
    }
}