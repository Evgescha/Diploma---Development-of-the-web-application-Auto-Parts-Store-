package com.hescha.autochapterstore.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Role extends AbstractEntity {
    private String role;
    @ManyToMany
    private List<User> users = new ArrayList<>();

    @Override
    public String toString() {
        return role;
    }
}
