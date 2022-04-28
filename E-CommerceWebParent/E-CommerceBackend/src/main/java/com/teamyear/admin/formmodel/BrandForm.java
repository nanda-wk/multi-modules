package com.teamyear.admin.formmodel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@Getter
@Setter
public class BrandForm {

    private Integer id;

    @NotEmpty
    private String brandName;

    private String brandDescription;

    private MultipartFile brandLogo;
}