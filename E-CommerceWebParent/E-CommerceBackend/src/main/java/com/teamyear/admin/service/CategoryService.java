package com.teamyear.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.teamyear.admin.repository.CategoryRepository;
import com.teamyear.common.entity.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    public <S extends Category> S save(S entity) {
        return categoryRepository.save(entity);
    }

}