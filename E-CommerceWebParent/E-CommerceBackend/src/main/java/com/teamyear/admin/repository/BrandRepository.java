package com.teamyear.admin.repository;

import java.util.List;


import com.teamyear.common.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query("select b from Brand b where b.brandName = ?1")
    List<Brand> findByBrandName(String brandName);
}