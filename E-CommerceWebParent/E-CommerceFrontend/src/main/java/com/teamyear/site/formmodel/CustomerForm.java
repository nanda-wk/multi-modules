package com.teamyear.site.formmodel;

import com.teamyear.common.entity.City;
import com.teamyear.common.entity.Region;
import com.teamyear.common.entity.Township;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.sql.Date;

@Getter
@Setter
public class CustomerForm {

    private Integer id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirm;

    @NotEmpty
    private String email;

    private Date dob;

    @NotEmpty
    private String gender;

    @NotEmpty
    private String phone;

    private Region region;

    private City city;

    private Township township;

    @NotEmpty
    private String address1;

    private String address2;

}