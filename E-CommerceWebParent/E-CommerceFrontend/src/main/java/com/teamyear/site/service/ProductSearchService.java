package com.teamyear.site.service;

import com.teamyear.common.entity.Product;
import com.teamyear.site.formmodel.ProductFilterForm;
import com.teamyear.site.repository.ProductSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSearchService {

    @Autowired
    private ProductSearchRepository productSearchRepository;

    public List<Product> findAll() {
        return productSearchRepository.findAll();
    }

    @Query("select p from Product p where p.category.id = :categoryId or p.brand.id = :brandId or p.subCategory.id = :subCategoryId")
    public List<Product> productFilter(ProductFilterForm filter) {
        List<Product> productList = productSearchRepository.productFilter(filter.getBrandId(), filter.getCategoryId(), filter.getSubCategoryId());
        if (filter.getCategoryId() == null && filter.getBrandId() == null && filter.getSubCategoryId() == null) {
            productList = productSearchRepository.findAll();
        }
        return productList;
    }

    @Query("select p from Product p where p.productName like %?1%" +
            "or p.productDescritpion like %?1%" +
            "or p.brand.brandName like  %?1%" +
            "or p.category.categoryName like %?1%" +
            "or p.subCategory.subCategoryName like %?1%")
    public List<Product> productSearch(String keyword) {
        return productSearchRepository.productSearch(keyword);
    }

    public Optional<Product> findById(Integer integer) {
        return productSearchRepository.findById(integer);
    }
}