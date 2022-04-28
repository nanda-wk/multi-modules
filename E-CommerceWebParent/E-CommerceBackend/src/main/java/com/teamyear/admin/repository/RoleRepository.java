package com.teamyear.admin.repository;

import java.util.List;

import com.teamyear.common.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    ;
    @Query("select r from Role r where r.roleName = ?1")
    List<Role> findByRoleName(String roleName);
}