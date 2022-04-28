package com.teamyear.admin.formmodel;

import javax.validation.constraints.NotEmpty;

public class CategoryForm {
    private Integer id;

    @NotEmpty
    private String categoryName;

    private String categoryDescritpion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescritpion() {
        return categoryDescritpion;
    }

    public void setCategoryDescritpion(String categoryDescritpion) {
        this.categoryDescritpion = categoryDescritpion;
    }

}