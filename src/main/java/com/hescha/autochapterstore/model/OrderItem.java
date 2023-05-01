package com.hescha.autochapterstore.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class OrderItem extends AbstractEntity {
    @ManyToOne
    private Product product;
    private Integer count;

    @Override
    public String toString() {
        return "OrderItem{" +
                ", product=" + product.id +
                ", count=" + count +
                '}';
    }
}