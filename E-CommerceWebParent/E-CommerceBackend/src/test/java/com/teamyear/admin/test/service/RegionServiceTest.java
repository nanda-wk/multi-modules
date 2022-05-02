package com.teamyear.admin.test.service;

import com.teamyear.admin.repository.RegionRepository;
import com.teamyear.admin.service.RegionService;
import com.teamyear.common.entity.Region;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.*;

@SpringBootTest
public class RegionServiceTest {

    @InjectMocks
    private RegionService service;

    @Mock
    private RegionRepository repository;

    private Region region() {
        Region r = new Region();
        r.setId(1);
        r.setName("Test Region Name");
        return r;
    }

    @Test
    void finAllTest() {
        Sort sort = Sort.by("name").ascending();
        service.findAll();
        verify(repository, times(1)).findAll(sort);
    }

    @Test
    void checkRegionNameTest() {
        service.checkRegionName(region().getName());
        verify(repository, times(1)).findByRegionName(region().getName());
    }

    @Test
    void saveTest() {
        Region r = region();
        service.save(r);
        verify(repository, times(1)).save(r);
    }

    @Test
    void findByIdTest() {
        service.findById(region().getId());
        verify(repository, times(1)).findById(region().getId());
    }

    @Test
    void deleteByIdTest() {
        service.deleteById(region().getId());
        verify(repository, times(1)).deleteById(region().getId());
    }
}