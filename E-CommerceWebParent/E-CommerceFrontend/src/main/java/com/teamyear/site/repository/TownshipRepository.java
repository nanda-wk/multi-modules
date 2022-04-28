package com.teamyear.site.repository;

import com.teamyear.common.entity.Township;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TownshipRepository extends JpaRepository<Township, Integer> {

    @Query("select t from Township t where t.name = ?1")
    List<Township> findByTownshipName(String name);
}