package com.hescha.autochapterstore.model;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Disk extends Product {
    private String type;
    private Double width;
    private Double diameter;
    private Double pcd1;
    private Double dia;
    private Double departure;
    private Integer mountingHoles;
    private Category category = Category.DISK;
}