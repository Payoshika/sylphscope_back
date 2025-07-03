package com.scholarship.scholarship.auth;

public enum Role {
    ADMIN,
    STUDENT,
    PROVIDER;
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}