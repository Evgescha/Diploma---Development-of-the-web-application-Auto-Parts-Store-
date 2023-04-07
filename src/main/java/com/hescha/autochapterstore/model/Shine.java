package com.hescha.autochapterstore.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Shine extends Product{
    private String type;
    private Double width;
    private Double diameter;
    private String productSubtype;
    private String season;
    private Category category = Category.SHINE;
}