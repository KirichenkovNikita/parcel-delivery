package com.guavapay.apigate.model;

public enum Roles {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),
    COURIER("ROLE_COURIER");

    private final String label;

    Roles(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
