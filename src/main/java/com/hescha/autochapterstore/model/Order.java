package com.hescha.autochapterstore.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "myOrder")
@Data
public class Order extends AbstractEntity {
    @OneToMany
    private List<OrderItem> orderitems = new ArrayList<>();
    private LocalDateTime created = LocalDateTime.now();
    private OrderStatus status = OrderStatus.CREATED;

    @Override
    public String toString() {
        return "Order{" +
                "orderitems=" + orderitems +
                ", created=" + created +
                ", status=" + status +
                '}';
    }

    public double getPrice() {
        return getOrderitems()
                .stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getCount())
                .sum();
    }
}
