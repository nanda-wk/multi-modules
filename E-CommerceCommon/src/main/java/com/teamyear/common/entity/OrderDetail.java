package com.teamyear.common.entity;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "sub_total")
    private Double subtotal;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Orders orders;

    public OrderDetail() {
    }

    public OrderDetail(Integer quantity, Double subtotal, Product product, Orders orders) {
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.product = product;
        this.orders = orders;
    }

    public OrderDetail(Integer id, Integer quantity, Double subtotal, Product product, Orders orders) {
        this.id = id;
        this.quantity = quantity;
        this.subtotal = subtotal;
        this.product = product;
        this.orders = orders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Orders getProductOrder() {
        return orders;
    }

    public void setProductOrder(Orders orders) {
        this.orders = orders;
    }
}