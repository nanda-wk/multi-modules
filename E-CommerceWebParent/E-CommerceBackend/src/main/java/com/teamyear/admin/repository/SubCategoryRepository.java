package com.teamyear.admin.repository;

import java.util.List;

import com.teamyear.common.entity.SubCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {

    @Query("select s from SubCategory s where s.subCategoryName = ?1")
    List<SubCategory> findBySubCategoryName(String subCategoryName);
}