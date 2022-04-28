package com.teamyear.admin.formmodel;

import com.teamyear.common.entity.Region;

import javax.validation.constraints.NotEmpty;

public class CityForm {

    private Integer id;

    @NotEmpty
    private String name;

    private Region region;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}