package com.teamyear.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "brand")
@Getter
@Setter
public class Brand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "brand_name", nullable = false, unique = true)
    private String brandName;

    @Column(name = "brand_description")
    private String brandDescription;

    @Column(name = "brand_logo",nullable = false)
    private String brandLogo;
}