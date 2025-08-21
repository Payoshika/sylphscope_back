package com.scholarship.scholarship.auth;

public enum Role {
    ADMIN,
    STUDENT,
    PROVIDER,
    TEMPORARY;
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}