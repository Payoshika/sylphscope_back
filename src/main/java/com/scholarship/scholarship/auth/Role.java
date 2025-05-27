package com.scholarship.scholarship.auth;

public enum Role {
    ADMIN,
    ISSUER,
    RECEIVER,
    VERIFIER;

    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}