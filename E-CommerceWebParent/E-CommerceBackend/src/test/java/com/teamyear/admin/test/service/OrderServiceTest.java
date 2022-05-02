package com.teamyear.admin.test.service;

import com.teamyear.admin.repository.OrdersRepository;
import com.teamyear.admin.service.OrdersService;
import com.teamyear.common.entity.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @InjectMocks
    private OrdersService service;

    @Mock
    private OrdersRepository repository;

    private Orders orders() {
        Orders o = new Orders();
        Region r = new Region();
        City c = new City();
        Township t = new Township();
        Customer cus = new Customer();

        r.setId(1);
        r.setName("Test Region Name");

        c.setId(1);
        c.setName("Test City Name");

        t.setId(1);
        t.setName("Test Township Name");

        cus.setId(1);
        cus.setFirstName("Test First Name");
        cus.setLastName("Test Last Name");
        cus.setUsername("Test Username");
        cus.setPassword("Test Password");
        cus.setEmail("Test Email");
        cus.setDob(new Date(System.currentTimeMillis()));
        cus.setGender("Test Gender");
        cus.setPhone("098283832");
        cus.setRole("Customer");
        cus.setRegion(r);
        cus.setCity(c);
        cus.setTownship(t);
        cus.setAddress("Test Customer Address");
        cus.setAddressOpl("Test Customer Optional Address");
        cus.setEnable(true);

        o.setOrderId("ORD001");
        o.setNote("Test Order Note");
        o.setTotal(99.89);
        o.setOrderStatus(OrderStatus.DELIVERED);
        o.setPaymentMethod(PaymentMethod.COD);
        o.setOrderTime(new Date(System.currentTimeMillis()));
        o.setRegion(r);
        o.setCity(c);
        o.setTownship(t);
        o.setAddress("Test Order Address");
        o.setStatus(true);
        o.setCustomer(cus);
        o.adOrderDetails(1, 98.9, new Product());

        return o;
    }

    @Test
    void findAllTest() {
        Orders o = orders();
        service.findAll();
        verify(repository, times(1)).findAll();
    }

    @Test
    void saveTest() {
        Orders o = orders();
        service.save(o);
        verify(repository, times(1)).save(o);
    }

    @Test
    void findByIdTest() {
        service.findById(orders().getOrderId());
        verify(repository, times(1)).findById(orders().getOrderId());
    }

    @Test
    void countTest() {
        service.count();
        verify(repository, times(1)).count();
    }

}