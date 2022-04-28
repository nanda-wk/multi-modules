package com.teamyear.admin.formmodel;

import javax.validation.constraints.NotEmpty;

public class DiscountForm {

    private Integer id;

    @NotEmpty
    private String discountName;

    private Integer discountPercent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDiscountName() {
		return discountName;
	}

	public void setDiscountName(String discountName) {
		this.discountName = discountName;
	}

	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}
}