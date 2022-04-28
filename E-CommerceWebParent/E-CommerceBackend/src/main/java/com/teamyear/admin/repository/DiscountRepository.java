package com.teamyear.admin.repository;

import com.teamyear.common.entity.Discount;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {

	@Query ("select d from Discount d where d.discountName =?1")
	List<Discount> findByDiscountName(String discountName);
}