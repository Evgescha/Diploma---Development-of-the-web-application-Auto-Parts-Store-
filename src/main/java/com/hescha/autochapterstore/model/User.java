package com.hescha.autochapterstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Table(name = "myUser")
@Entity
public class User extends AbstractEntity {
    @Column(unique = true)
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
}
