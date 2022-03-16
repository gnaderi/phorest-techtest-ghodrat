package com.naderi.phorest.salon.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;

public enum ServiceType {
    SERVICE("service"), PURCHASE("purchase");
    private String value;

    ServiceType(String typeValue) {
        this.value = typeValue;
    }

    @JsonCreator
    public static ServiceType of(String name) {
        ServiceType category = resolve(name);
        if (category == null) {
            throw new IllegalArgumentException("No matching Service Type for value[" + name + "]");
        }
        return category;
    }

    @Nullable
    private static ServiceType resolve(String name) {
        for (ServiceType category : values()) {
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
