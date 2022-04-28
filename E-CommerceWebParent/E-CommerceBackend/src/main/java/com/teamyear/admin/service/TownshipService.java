package com.teamyear.admin.service;

import com.teamyear.admin.repository.TownshipRepository;
import com.teamyear.common.entity.Township;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TownshipService {

    @Autowired
    private TownshipRepository townshipRepository;

    @Query("select t from Township t where t.name = ?1")
    public boolean checkTownshipName(String name) {
        boolean check;
        List<Township> townshipList = townshipRepository.findByTownshipName(name);
        check = townshipList.size() > 0;
        return check;
    }

    public List<Township> findAll() {
        Sort sort = Sort.by("name").ascending();
        List<Township> townshipList = new ArrayList<>();
        townshipRepository.findAll(sort).forEach(townshipList::add);
        return townshipRepository.findAll();
    }

    public <S extends Township> S save(S entity) {
        return townshipRepository.save(entity);
    }

    public Optional<Township> findById(Integer integer) {
        return townshipRepository.findById(integer);
    }

    public void deleteById(Integer integer) {
        townshipRepository.deleteById(integer);
    }
}