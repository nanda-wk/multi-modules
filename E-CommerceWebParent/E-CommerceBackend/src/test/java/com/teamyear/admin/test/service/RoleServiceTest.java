package com.teamyear.admin.test.service;

import com.teamyear.admin.repository.RoleRepository;
import com.teamyear.admin.service.RoleService;
import com.teamyear.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;

@SpringBootTest
public class RoleServiceTest {

    @InjectMocks
    private RoleService service;

    @Mock
    private RoleRepository repository;

    private Role role() {
        Role r = new Role();
        r.setId(1);
        r.setRoleName("Test Role Name");
        r.setDescription("Test Role Description");
        return r;
    }

    @Test
    void saveTest() {
        Role r = role();
        service.save(r);
        verify(repository, times(1)).save(r);
    }

    @Test
    void findAllTest() {
        service.findAll();
        verify(repository, times(1)).findAll();
    }

    @Test
    void deleteByIdTest() {
        service.deleteById(role().getId());
        verify(repository, times(1)).deleteById(role().getId());
    }

    @Test
    void findByRoleName() {
        service.findByRoleName(role().getRoleName());
        verify(repository, times(1)).findByRoleName(role().getRoleName());
    }

    @Test
    void findById() {
        service.findById(role().getId());
        verify(repository, times(1)).findById(role().getId());
    }
}