package com.teamyear.admin.test.service;

import com.teamyear.common.entity.Announcement;
import com.teamyear.site.repository.AnnouncementRepository;
import com.teamyear.site.service.AnnouncementService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.*;

@SpringBootTest
public class FAnnouncementServiceTest {

    @InjectMocks
    private AnnouncementService service;

    @Mock
    private AnnouncementRepository repository;

    @Test
    void finaAllTest() {
        service.findAll();
        verify(repository, times(1)).findAll(Sort.by("anmName"));
    }
}