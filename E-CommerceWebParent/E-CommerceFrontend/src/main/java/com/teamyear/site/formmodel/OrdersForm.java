package com.teamyear.site.formmodel;

import com.teamyear.common.entity.City;
import com.teamyear.common.entity.PaymentMethod;
import com.teamyear.common.entity.Region;
import com.teamyear.common.entity.Township;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrdersForm {

    private String orderId;

    private Double total;

    private PaymentMethod paymentMethod;

    private Region region;

    private City city;

    private Township township;

}