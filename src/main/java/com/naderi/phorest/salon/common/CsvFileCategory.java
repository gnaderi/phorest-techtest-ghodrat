package com.naderi.phorest.salon.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import org.springframework.lang.Nullable;

public enum CsvFileCategory {
    CLIENT("client"), APPOINTMENT("appointment"), SERVICE("service"), PURCHASE("purchase");
    private String value;

    CsvFileCategory(String typeValue) {
        this.value = typeValue;
    }

    @JsonCreator
    public static CsvFileCategory of(String name) {
        CsvFileCategory csvFileCategory = resolve(name);
        if (csvFileCategory == null) {
            throw new IllegalArgumentException("No matching CsvFileCategory for value[" + name + "]");
        }
        return csvFileCategory;
    }

    @Nullable
    private static CsvFileCategory resolve(String name) {
        for (CsvFileCategory csvFileCategory : values()) {
            if (csvFileCategory.getValue().equalsIgnoreCase(name)) {
                return csvFileCategory;
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
