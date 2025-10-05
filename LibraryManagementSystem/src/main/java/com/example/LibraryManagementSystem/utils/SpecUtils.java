package com.example.LibraryManagementSystem.utils;

import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;
import java.util.function.Function;

@UtilityClass
public class SpecUtils {
    public <T, V> Specification<T> optional(V value, Function<V, Specification<T>> function) {
        return value == null ? null : function.apply(value);
    }

    public <T, V, C extends Collection<V>>
    Specification<T> optionalNotEmpty(C collection, Function<C, Specification<T>> function) {
        return (collection == null || collection.isEmpty()) ? null : function.apply(collection);
    }
}
