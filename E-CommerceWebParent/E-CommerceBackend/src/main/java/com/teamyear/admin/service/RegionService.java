package com.teamyear.admin.service;

import com.teamyear.admin.repository.RegionRepository;
import com.teamyear.common.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> findAll() {
        Sort sort = Sort.by("name").ascending();
        List<Region> regionList = new ArrayList<>();
        regionRepository.findAll(sort).forEach(regionList::add);
        return regionList;
    }

    public boolean checkRegionName(String name) {
        boolean check;
        List<Region> regionList = regionRepository.findByRegionName(name);
        check = regionList.size() > 0;
        return check;
    }

    public <S extends Region> S save(S entity) {
        return regionRepository.save(entity);
    }

    public Optional<Region> findById(Integer integer) {
        return regionRepository.findById(integer);
    }

    public void deleteById(Integer integer) {
        regionRepository.deleteById(integer);
    }


}