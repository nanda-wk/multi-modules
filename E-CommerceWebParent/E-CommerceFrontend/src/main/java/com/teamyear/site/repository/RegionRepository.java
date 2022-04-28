package com.teamyear.site.repository;

import com.teamyear.common.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegionRepository extends JpaRepository<Region, Integer> {

    @Query("select r from Region r where r.name = ?1")
    List<Region> findByRegionName(String name);
}