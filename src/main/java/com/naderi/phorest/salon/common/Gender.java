package com.naderi.phorest.salon.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;

public enum Gender {
    MALE("Male"), FEMALE("Female");
    private String value;

    Gender(String typeValue) {
        this.value = typeValue;
    }

    @JsonCreator
    public static Gender of(String name) {
        Gender gender = resolve(name);
        if (gender == null) {
            throw new IllegalArgumentException("No matching Gender for value[" + name + "]");
        }
        return gender;
    }

    @Nullable
    private static Gender resolve(String name) {
        for (Gender gender : values()) {
            if (gender.getValue().equalsIgnoreCase(name)) {
                return gender;
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
