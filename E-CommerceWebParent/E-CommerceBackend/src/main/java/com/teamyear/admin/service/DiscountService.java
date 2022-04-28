package com.teamyear.admin.service;

import java.util.ArrayList;
import java.util.List;

import com.teamyear.admin.repository.DiscountRepository;
import com.teamyear.common.entity.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    @Autowired
    private DiscountRepository discountRepository;

    public Discount save(Discount discount) {
        return discountRepository.save(discount);
    }

    public List<Discount> findAll() {
    	Sort sortDiscountName = Sort.by("discountName").ascending();
    	List<Discount> discountList = new ArrayList<>();
    	discountRepository.findAll(sortDiscountName).forEach(discountList::add);
    	return discountList;
    }

    public List<Discount> findByDiscountName(String discountName){
    	return discountRepository.findByDiscountName(discountName);
    }

    public Discount findById(Integer id) {
        return discountRepository.findById(id).get();
    }

    public void deleteById(Integer id) {
        discountRepository.deleteById(id);
    }

}