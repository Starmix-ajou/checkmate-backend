package com.starmix.checkmate.domain.user;

import lombok.Getter;

@Getter
public enum Role {
    PRODUCT_MANAGER("Product Manager"), DEVELOPER("Developer");

    private final String description;

    Role(String description) {
        this.description = description;
    }
}
