package com.teamyear.admin.formmodel;

import javax.validation.constraints.NotEmpty;

import com.teamyear.common.entity.Category;

public class SubCategoryForm {

    private Integer id;

    @NotEmpty
    private String subCategoryName;

    private Category category;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}