package com.uade.financialEntity.utils;

public class PairObject {

    private String key;
    private Object value;

    public PairObject(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }
}
