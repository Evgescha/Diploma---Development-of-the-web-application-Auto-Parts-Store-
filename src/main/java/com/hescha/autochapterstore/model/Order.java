package com.hescha.autochapterstore.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "myOrder")
@Data
public class Order extends AbstractEntity{
    @ManyToOne
    private User owner;
    @OneToMany
    private List<OrderItem> orderitems = new ArrayList<>();
    private LocalDateTime created = LocalDateTime.now();
    private OrderStatus status = OrderStatus.CREATED;
}
