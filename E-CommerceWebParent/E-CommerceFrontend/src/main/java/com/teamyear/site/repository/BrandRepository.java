package com.teamyear.site.repository;

import com.teamyear.common.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {

    @Query("select b from Brand b where b.brandName = ?1")
    List<Brand> findByBrandName(String brandName);
}