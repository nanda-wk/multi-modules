package com.teamyear.admin.service;

import com.teamyear.admin.repository.UserRepository;
import com.teamyear.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private BCryptPasswordEncoder encoder;

    @Query("select u from User u where u.username = ?1")
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        if(user.getId() == null){
            user.setCreatedDate(new Date());
        }
        user.setUpdatedDate(new Date());
//        String encodedPwd = encoder.encode(user.getPassword());
//        user.setPassword(encodedPwd);
        return userRepository.save(user);
    }

    public Optional<User> findById(Integer integer) {
        return userRepository.findById(integer);
    }

    public void deleteById(Integer integer) {
        userRepository.deleteById(integer);
    }

}