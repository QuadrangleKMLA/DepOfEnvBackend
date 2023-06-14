package com.quadrangle.depofenvkmla.Documents.User;

import lombok.Getter;

@Getter
public enum ERole {
    ROLE_ADMIN("admin"),
    ROLE_MINISTER("minister");

    private final String role;

    ERole(String role) {
        this.role = role;
    }
}
