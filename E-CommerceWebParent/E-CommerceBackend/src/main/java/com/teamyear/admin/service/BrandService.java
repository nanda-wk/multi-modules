package com.teamyear.admin.service;

import com.teamyear.admin.repository.BrandRepository;
import com.teamyear.common.entity.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public void save(Brand brand) {
        brandRepository.save(brand);
    }

    public List<Brand> findAll() {
        Sort sortBrandName = Sort.by("brandName").ascending();
        List<Brand> brandList = new ArrayList<>();
        brandRepository.findAll(sortBrandName).forEach(brandList::add);
        return brandList;
    }

    public Brand findById(Integer id) {
        return brandRepository.findById(id).get();
    }

    public List<Brand> findByBrandName(String brandName) {
        return brandRepository.findByBrandName(brandName);
    }

    public void deleteById(Integer id) {
        brandRepository.deleteById(id);
    }


}