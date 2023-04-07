package com.hescha.autochapterstore.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public enum Category{
    OIL,
    ACCUMULATOR,
    DISK,
    SHINE,
    OTHER
}
