package com.teamyear.admin.service;

import java.util.ArrayList;
import java.util.List;

import com.teamyear.admin.repository.SubCategoryRepository;
import com.teamyear.common.entity.SubCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SubCategoryService {

    @Autowired
    SubCategoryRepository subCategoryRepository;

    public List<SubCategory> findBySubCategoryName(String subCategoryName) {
        return subCategoryRepository.findBySubCategoryName(subCategoryName);
    }

    public List<SubCategory> findAll() {
        Sort sortSubName = Sort.by("subCategoryName").ascending();
        List<SubCategory> subCategoryList = new ArrayList<>();
        subCategoryRepository.findAll(sortSubName).forEach(subCategoryList::add);
        return subCategoryList;
    }

    public SubCategory findById(Integer id) {
        return subCategoryRepository.findById(id).get();
    }

    public void deleteById(Integer id) {
        subCategoryRepository.deleteById(id);
    }

    public void save(SubCategory subCategory) {
        subCategoryRepository.save(subCategory);
    }

}