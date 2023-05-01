package com.hescha.autochapterstore.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
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

}
