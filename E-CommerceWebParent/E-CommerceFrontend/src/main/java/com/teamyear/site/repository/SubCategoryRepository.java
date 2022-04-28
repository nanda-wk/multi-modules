package com.teamyear.site.repository;

import com.teamyear.common.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    @Query("select s from SubCategory s where s.subCategoryName = ?1")
    List<SubCategory> findBySubCategoryName(String subCategoryName);
}