package com.teamyear.site.service;

import com.teamyear.common.entity.City;
import com.teamyear.site.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepository cityRepository;

    @Query("select c from City c where c.name = ?1")
    public boolean checkCityName(String name) {
        boolean check;
        List<City> cityList = cityRepository.findByCityName(name);
        check = cityList.size() > 0;
        return check;
    }

    public List<City> findAll() {
        Sort sort = Sort.by("name").ascending();
        List<City> cityList = new ArrayList<>();
        cityRepository.findAll(sort).forEach(cityList::add);
        return cityList;
    }

    public <S extends City> S save(S entity) {
        return cityRepository.save(entity);
    }

    public Optional<City> findById(Integer integer) {
        return cityRepository.findById(integer);
    }

    public void deleteById(Integer integer) {
        cityRepository.deleteById(integer);
    }
}