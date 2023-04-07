package com.hescha.autochapterstore.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
public class OrderItem extends AbstractEntity {
    @ManyToOne
    private Order order;
    @ManyToOne
    private Product product;
    private Integer count;
}
