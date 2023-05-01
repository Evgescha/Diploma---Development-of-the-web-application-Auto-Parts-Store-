package com.hescha.autochapterstore.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Role extends AbstractEntity {
    private String role;
    @Override
    public String toString() {
        return role;
    }
}
