package com.teamyear.admin.formmodel;

import com.teamyear.common.entity.City;

import javax.validation.constraints.NotEmpty;

public class TownshipForm {

    private Integer id;

    @NotEmpty
    private String name;

    private City city;

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

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}