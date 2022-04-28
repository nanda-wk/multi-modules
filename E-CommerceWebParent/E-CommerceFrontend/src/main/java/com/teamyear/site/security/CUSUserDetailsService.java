package com.teamyear.site.security;

import com.teamyear.common.entity.Customer;
import com.teamyear.site.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CUSUserDetailsService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepository.getCustomerByUsername(username);
        if (customer != null) {
            return new CUSUserDetails(customer);
        }
        throw new UsernameNotFoundException("Could not find customer with username: " + username);
    }
}