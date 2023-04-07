package com.hescha.autochapterstore.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Other extends Product {
    private Category category = Category.OTHER;
}