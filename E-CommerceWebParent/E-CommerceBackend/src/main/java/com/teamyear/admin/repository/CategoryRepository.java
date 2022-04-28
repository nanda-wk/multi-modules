package com.teamyear.admin.repository;

import java.util.List;

import com.teamyear.common.entity.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query ("select c from Category c where c.categoryName = ?1")
    List<Category> findByCategoryName(String categoryName);
}