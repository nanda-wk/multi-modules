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

	public List<Announcement> findAll() {
		Sort sortAnmName = Sort.by("anmName").ascending();
		List<Announcement> anmList = new ArrayList<>();
		anmRepository.findAll(sortAnmName).forEach(anmList::add);
		return anmList;
	}
}