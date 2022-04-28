package com.teamyear.site.service;

import com.teamyear.common.entity.Customer;
import com.teamyear.site.repository.CustomerRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer save(Customer customer) {
        if (customer.getId() == null) {
            customer.setCreatedTime(new Date());
        }
        String encodedPwd = encoder.encode(customer.getPassword());
        customer.setPassword(encodedPwd);
        return customerRepository.save(customer);
    }

    public Customer findById(Integer integer) {
        return customerRepository.findById(integer).get();
    }

    public void deleteById(Integer integer) {
        customerRepository.deleteById(integer);
    }

    @Query("select c from Customer c where c.username =?1")
    public List<Customer> findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    @Query("select c from Customer c where c.email = ?1")
    public List<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Query("select c from Customer c where c.username = ?1")
    public Customer getCustomerByUsername(String username) {
        return customerRepository.getCustomerByUsername(username);
    }

    @Query("select c from Customer c where c.verificationCode = ?1")
    public boolean verify(String verify) {
        Customer customer = customerRepository.findByVerification(verify);
        if(customer == null || customer.isEnable()) {
            return false;
        } else {
            customer.setVerificationCode(null);
            customer.setEnable(true);
            customerRepository.save(customer);
        return true;
        }
    }

    @Query("select c from Customer c where c.resetPasswordToken = ?1")
    public Customer resetPasswordToken(String email) throws Exception {
        Customer customer = customerRepository.findByEmail(email).get(0);
        if(customer != null) {
            String token = RandomString.make(30);
            customer.setResetPasswordToken(token);
            Customer updatedCustomer = customerRepository.save(customer);
            return updatedCustomer;
        } else {
            throw new Exception("Could not find any customer with the email " + email);
        }
    }

    @Query("select c from Customer c where c.resetPasswordToken = ?1")
    public Customer findByResetPasswordToken(String token) {
        return customerRepository.findByResetPasswordToken(token);
    }

    public boolean checkUsername(String username) {
        boolean check;
        List<Customer> customerList = customerRepository.findByUsername(username);
        check = customerList.size() > 0;
        return check;
    }

    public boolean checkEmail(String email) {
        boolean check;
        List<Customer> customerList = customerRepository.findByUsername(email);
        check = customerList.size() > 0;
        return check;
    }

    public void updatePassword(String token, String password) throws Exception {
        Customer customer = customerRepository.findByResetPasswordToken(token);
        if(customer == null){
            throw new Exception("Invalid token.");
        }

        customer.setPassword(encoder.encode(password));
        customer.setResetPasswordToken(null);
        customerRepository.save(customer);
    }
}