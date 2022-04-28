package com.teamyear.admin.service;

import java.util.ArrayList;
import java.util.List;

import com.teamyear.admin.repository.AnnouncementRepository;
import com.teamyear.common.entity.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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