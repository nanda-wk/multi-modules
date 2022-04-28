package com.teamyear.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "announcement")
public class Announcement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name= "announcement_name")
	private String anmName;

	@Column(name = "announcement_image")
	private String anmImage;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAnmName() {
		return anmName;
	}

	public void setAnmName(String anmName) {
		this.anmName = anmName;
	}

	public String getAnmImage() {
		return anmImage;
	}

	public void setAnmImage(String anmImage) {
		this.anmImage = anmImage;
	}



}