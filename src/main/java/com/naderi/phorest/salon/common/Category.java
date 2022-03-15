package com.naderi.phorest.salon.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;

public enum Category {
    CLIENT("client"), APPOINTMENT("appointment"), SERVICE("service"), PURCHASE("purchase");
    private String value;

    Category(String typeValue) {
        this.value = typeValue;
    }

    @JsonCreator
    public static Category of(String name) {
        Category category = resolve(name);
        if (category == null) {
            throw new IllegalArgumentException("No matching Category for value[" + name + "]");
        }
        return category;
    }

    @Nullable
    private static Category resolve(String name) {
        for (Category category : values()) {
            if (category.getValue().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
