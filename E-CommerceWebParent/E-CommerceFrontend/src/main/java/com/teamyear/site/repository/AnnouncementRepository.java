package com.teamyear.site.repository;

import com.teamyear.common.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {

	@Query("select a from Announcement a where a.anmName = ?1")
	List<Announcement> findByAnnouncementName(String anmName);
}