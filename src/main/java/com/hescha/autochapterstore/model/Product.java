package com.hescha.autochapterstore.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
//@Embeddable
public abstract class Product extends AbstractEntity {
    private String name;
    @Column(length = 2500)
    private String description;
    private String image;
    private Double price;
    @OneToMany
    private List<Comment> comments = new ArrayList<>();
    private Boolean deleted;
}
