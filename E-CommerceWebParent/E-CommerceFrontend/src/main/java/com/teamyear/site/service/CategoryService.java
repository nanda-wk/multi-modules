package com.teamyear.site.service;

import com.teamyear.common.entity.Category;
import com.teamyear.site.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> findAll() {
        Sort sortCategoryName = Sort.by("categoryName").ascending();
        List<Category> categoryList = new ArrayList<>();
        categoryRepository.findAll(sortCategoryName).forEach(categoryList::add);
        return categoryList;
    }

    public List<Category> findByCategoryName(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName);
    }

    public Category findById(Integer id) {
        return categoryRepository.findById(id).get();
    }

    public <S extends Category> S save(S entity) {
        return categoryRepository.save(entity);
    }

}