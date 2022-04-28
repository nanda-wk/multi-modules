package com.teamyear.admin.formmodel;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class AnnouncementForm {

	private Integer id;

	@NotEmpty
	private String anmName;

	private MultipartFile anmImage;



}