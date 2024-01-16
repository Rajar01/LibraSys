package com.librasys.enums;

public enum UserRole {
    ADMIN("admin"), STUDENT("student");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
