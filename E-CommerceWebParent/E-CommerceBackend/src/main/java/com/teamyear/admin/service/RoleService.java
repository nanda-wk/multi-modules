package com.teamyear.admin.service;

import java.util.List;

import com.teamyear.admin.repository.RoleRepository;
import com.teamyear.common.entity.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public <S extends Role> S save(S entity) {
        return roleRepository.save(entity);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public void deleteById(Integer id) {
        roleRepository.deleteById(id);
    }

    public List<Role> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public Role findById(Integer id) {
        return roleRepository.findById(id).get();
    }

}