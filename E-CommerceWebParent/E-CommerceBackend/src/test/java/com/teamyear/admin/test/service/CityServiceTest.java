package com.teamyear.admin.test.service;

import com.teamyear.admin.repository.CityRepository;
import com.teamyear.admin.service.CityService;
import com.teamyear.common.entity.City;
import com.teamyear.common.entity.Region;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.*;


@SpringBootTest
public class CityServiceTest {

    @InjectMocks
    private CityService service;

    @Mock
    private CityRepository repository;

    private City city() {
        City c = new City();
        Region r = new Region();
        r.setId(1);
        r.setName("Test Region Name");

        c.setId(1);
        c.setName("Test City Name");
        c.setRegion(r);
        return c;
    }

    @Test
    void checkCityNameTest() {
        City c = city();
        service.checkCityName(c.getName());
        verify(repository, times(1)).findByCityName(c.getName());
    }

    @Test
    void findAllTest() {
        Sort sort = Sort.by("name").ascending();
        service.findAll();
        verify(repository, times(1)).findAll(sort);
    }

    @Test
    void saveTest() {
        City c = city();
        service.save(c);
        verify(repository, times(1)).save(c);
    }

    @Test
    void findByIdTest() {
        City c = city();
        service.findById(c.getId());
        verify(repository, times(1)).findById(c.getId());
    }

    @Test
    void deleteByIdTest() {
        City c = city();
        service.deleteById(c.getId());
        verify(repository, times(1)).deleteById(c.getId());
    }

}