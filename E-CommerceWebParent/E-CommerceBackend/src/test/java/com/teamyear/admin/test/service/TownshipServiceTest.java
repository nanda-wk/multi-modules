package com.teamyear.admin.test.service;

import com.teamyear.admin.repository.TownshipRepository;
import com.teamyear.admin.service.TownshipService;
import com.teamyear.common.entity.City;
import com.teamyear.common.entity.Region;
import com.teamyear.common.entity.Township;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.*;

@SpringBootTest
public class TownshipServiceTest {

    @InjectMocks
    private TownshipService service;

    @Mock
    private TownshipRepository repository;

    private Township township() {
        Township t = new Township();
        City c = new City();
        Region r = new Region();

        r.setId(1);
        r.setName("Test Region Name");

        c.setId(1);
        c.setName("Test City Name");
        c.setRegion(r);

        t.setId(1);
        t.setName("Test Township Name");
        t.setCity(c);
        return t;
    }

    @Test
    void checkTownshipNameTest() {
        service.checkTownshipName(township().getName());
        verify(repository, times(1)).findByTownshipName(township().getName());
    }

    @Test
    void findALlTest() {
        service.findAll();
        verify(repository, times(1)).findAll(Sort.by("name"));
    }

    @Test
    void saveTest() {
        Township t = township();
        service.save(t);
        verify(repository, times(1)).save(t);
    }

    @Test
    void findByIdTest() {
        service.findById(township().getId());
        verify(repository, times(1)).findById(township().getId());
    }

    @Test
    void deleteByIdTest() {
        service.deleteById(township().getId());
        verify(repository, times(1)).deleteById(township().getId());
    }
}