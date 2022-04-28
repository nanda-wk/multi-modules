package com.teamyear.site.service;

import com.teamyear.common.entity.Announcement;
import com.teamyear.site.repository.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnouncementService {

	@Autowired
	private AnnouncementRepository anmRepository;

	public void save(Announcement announcement) {
		anmRepository.save(announcement);
	}

	public Announcement findById(Integer id) {
		return anmRepository.findById(id).get();
	}

	public List<Announcement> findByAnnouncementName(String anmName) {
		return anmRepository.findByAnnouncementName(anmName);
	}

	public void deleteById(Integer id) {
		anmRepository.deleteById(id);
	}

	public List<Announcement> findAll() {
		Sort sortAnmName = Sort.by("anmName").ascending();
		List<Announcement> anmList = new ArrayList<>();
		anmRepository.findAll(sortAnmName).forEach(anmList::add);
		return anmList;
	}
}