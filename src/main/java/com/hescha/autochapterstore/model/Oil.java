package com.hescha.autochapterstore.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Oil extends Product{
    private String type;
    private String viscosity;
    private Double volume;
    private Category category = Category.OIL;
}
