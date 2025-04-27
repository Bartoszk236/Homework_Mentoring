package com.test.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Discount {
    private final String name;
    private final BigDecimal value;

    public Discount(String name, BigDecimal value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Discount discount = (Discount) o;
        return Objects.equals(name, discount.name) && Objects.equals(value, discount.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
