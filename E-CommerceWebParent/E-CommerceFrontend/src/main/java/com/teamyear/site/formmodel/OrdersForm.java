package com.teamyear.site.formmodel;

import com.teamyear.common.entity.PaymentMethod;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrdersForm {

    private String orderId;

    private Double total;

    private PaymentMethod paymentMethod;

}