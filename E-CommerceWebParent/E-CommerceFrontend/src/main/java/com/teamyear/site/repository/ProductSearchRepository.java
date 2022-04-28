package com.teamyear.site.repository;

import com.teamyear.common.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductSearchRepository extends JpaRepository<Product, Integer> {

    @Query("select p from Product p where p.category.id = :categoryId or p.brand.id = :brandId or p.subCategory.id = :subCategoryId")
    List<Product> productFilter(@Param("categoryId") Integer categoryId,
                                @Param("brandId") Integer brandId,
                                @Param("subCategoryId") Integer subCategoryId);

    @Query("select p from Product p where p.productName like %?1%" +
            "or p.productDescritpion like %?1%" +
            "or p.brand.brandName like  %?1%" +
            "or p.category.categoryName like %?1%" +
            "or p.subCategory.subCategoryName like %?1%")
    List<Product> productSearch(String keyword);
}