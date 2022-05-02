package com.teamyear.admin.test.service;

import com.teamyear.admin.repository.UserRepository;
import com.teamyear.admin.service.UserService;
import com.teamyear.common.entity.Role;
import com.teamyear.common.entity.User;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;

    private User user() {
        User u = new User();
        Role r = new Role();

        r.setId(1);
        r.setRoleName("Test Role Name");
        r.setDescription("Test Role Description");

        u.setId(1);
        u.setName("Test User Name");
        u.setUsername("Test User Username");
        u.setPassword("Test User Password");
        u.setCreatedDate(new Date());
        u.setUpdatedDate(new Date());
        u.addRole(r);
        return  u;
    }

    @Test
    void getUserByUsernameTest() {
        service.getUserByUsername(user().getUsername());
        verify(repository, times(1)).getUserByUsername(user().getUsername());
    }

    @Test
    void findALlTest() {
        service.findAll();
        verify(repository, times(1)).findAll();
    }

    @Test
    void saveTest() {
        User expected = user();
        when(service.save(expected)).thenReturn(expected);
        User actual = service.save(user());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByIdTest() {
        service.findById(user().getId());
        verify(repository, times(1)).findById(user().getId());
    }

    @Test
    void deleteByIdTest() {
        service.deleteById(user().getId());
        verify(repository, times(1)).deleteById(user().getId());
    }

    @Test
    void countTest() {
        service.count();
        verify(repository, times(1)).count();
    }
}