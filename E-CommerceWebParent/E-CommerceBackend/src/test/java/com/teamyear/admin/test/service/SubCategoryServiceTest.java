package com.teamyear.admin.test.service;

import com.teamyear.admin.repository.SubCategoryRepository;
import com.teamyear.admin.service.SubCategoryService;
import com.teamyear.common.entity.Category;
import com.teamyear.common.entity.SubCategory;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.*;

@SpringBootTest
public class SubCategoryServiceTest {

    @InjectMocks
    private SubCategoryService service;

    @Mock
    private SubCategoryRepository repository;

    private SubCategory sub() {
        SubCategory s = new SubCategory();
        Category c = new Category();

        c.setId(1);
        c.setCategoryName("Test Categhory Name");
        c.setCategoryDescription("Test Category Description");

        s.setId(1);
        s.setSubCategoryName("Test Sub Category Name");
        s.setCategory(c);
        return s;
    }

    @Test
    void findBySubCategoryNameTest() {
        service.findBySubCategoryName(sub().getSubCategoryName());
        verify(repository, times(1)).findBySubCategoryName(sub().getSubCategoryName());
    }

    @Test
    void findAllTest() {
        service.findAll();
        verify(repository, times(1)).findAll(Sort.by("subCategoryName"));
    }

    @Test
    void findByIdTest() {
        service.findById(sub().getId());
        verify(repository, times(1)).findById(sub().getId());
    }

    @Test
    void deleteByIdTest() {
        service.deleteById(sub().getId());
        verify(repository, times(1)).deleteById(sub().getId());
    }

    @Test
    void saveTest() {
        SubCategory s = sub();
        service.save(s);
        verify(repository, times(1)).save(s);
    }
}