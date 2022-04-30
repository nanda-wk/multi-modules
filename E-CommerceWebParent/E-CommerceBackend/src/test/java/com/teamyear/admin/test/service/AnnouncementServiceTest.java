package com.teamyear.admin.test.service;

import com.teamyear.admin.repository.AnnouncementRepository;
import com.teamyear.admin.service.AnnouncementService;
import com.teamyear.common.entity.Announcement;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AnnouncementServiceTest {

    @InjectMocks
    private AnnouncementService service;

    @Mock
    private AnnouncementRepository repository;

    private Announcement createAnnouncement() {
        Announcement announcement = new Announcement();
        announcement.setId(1);
        announcement.setAnmName("Test Announcement Name");
        announcement.setAnmImage("Test Image Path/TestImg.png");
        return announcement;
    }

    @Test
    void saveTest() {
        Announcement a = createAnnouncement();
        service.save(a);
        verify(repository,times(1)).save(a);

    }

    @Test
    void findByIdTest() {
        Announcement a = createAnnouncement();
        service.findById(a.getId());
        verify(repository, times(1)).findById(a.getId());
    }

    @Test
    void findAllTest() {
        Sort sortName = Sort.by("anmName").ascending();
        service.findAll();
        verify(repository, times(1)).findAll(sortName);
    }

    @Test
    void findByAnnouncementNameTest() {
        Announcement a = createAnnouncement();
        service.findByAnnouncementName(a.getAnmName());
        verify(repository, times(1)).findByAnnouncementName(a.getAnmName());
    }

    @Test
    void deleteByIdTest() {
        Announcement a = createAnnouncement();
        service.deleteById(a.getId());
        verify(repository, times(1)).deleteById(a.getId());
    }



}