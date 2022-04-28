package com.teamyear.admin.formmodel;

import javax.validation.constraints.NotEmpty;

public class RegionForm {

    private Integer id;

    @NotEmpty
    private String name;

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
}