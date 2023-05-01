package com.hescha.autochapterstore.model;

public enum Category{
    PROCESSOR,
    MOTHER_BOARD,
    RAM,
    SDD_HDD,
    VIDEOCARD,
    PERIFERIA;

    public Category fromString(String str){
        return Category.valueOf(str);
    }

}
