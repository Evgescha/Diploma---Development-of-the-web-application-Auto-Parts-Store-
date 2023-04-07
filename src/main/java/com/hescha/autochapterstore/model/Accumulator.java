package com.hescha.autochapterstore.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Accumulator extends Product{
    private String type;
    private String poleTerminals;
    private Double capacity;
    private Double voltage;
    private Category category = Category.ACCUMULATOR;
}