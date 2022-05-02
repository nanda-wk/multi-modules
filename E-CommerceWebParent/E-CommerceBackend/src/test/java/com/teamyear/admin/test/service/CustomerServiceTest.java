package com.teamyear.admin.test.service;

import com.teamyear.admin.repository.CustomerRepository;
import com.teamyear.admin.service.CustomerService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService service;

    @Mock
    private CustomerRepository repository;
}