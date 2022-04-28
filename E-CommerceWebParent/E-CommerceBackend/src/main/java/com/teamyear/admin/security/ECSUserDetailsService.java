package com.teamyear.admin.security;

import com.teamyear.admin.repository.UserRepository;
import com.teamyear.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ECSUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.getUserByUsername(username);
        if(user != null) {
            return new ECSUserDetails(user);
        } throw  new UsernameNotFoundException("Could not find user with username: " + username);
    }
}