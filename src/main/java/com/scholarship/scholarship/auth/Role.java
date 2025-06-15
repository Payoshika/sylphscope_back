package com.scholarship.scholarship.auth;

public enum Role {
    ADMIN,
    ISSUER,
    RECEIVER,
    SUPPORTER;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}