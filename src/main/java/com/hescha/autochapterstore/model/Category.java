package com.hescha.autochapterstore.model;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category{
    OIL ("Масло"),
    ACCUMULATOR ("Аккумулятор"),
    DISK ("Диски"),
    SHINE ("Шины"),
    OTHER ("Другое");

    final String name;

    public Category fromString(String str){
        return Category.valueOf(str);
    }

    @Override
    public String toString() {
        return name;
    }
}
