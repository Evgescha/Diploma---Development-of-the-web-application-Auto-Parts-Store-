package com.hescha.autochapterstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Table(name = "myUser")
@Entity
public class User extends AbstractEntity {
    private String username;
    @JsonIgnore
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String image;
    private String address;
    @ManyToMany
    private List<Role> roles = new ArrayList();
    @OneToMany
    private List<Order> orders = new ArrayList<>();

    @Override
    public String toString() {
        return username;
    }

    public boolean isManager() {
        return roles.stream().anyMatch(role -> role.getRole().contains("MANAGER"));
    }

    public boolean isAdmin() {
        return roles.stream().anyMatch(role -> role.getRole().contains("ADMIN"));
    }

    public boolean isManagerOrAdmin() {
        return isAdmin() || isManager();
    }

    public List<Order> getActiveOrders() {
        return orders.stream().filter(order -> order.getStatus() == OrderStatus.CREATED).collect(Collectors.toList());
    }
}
