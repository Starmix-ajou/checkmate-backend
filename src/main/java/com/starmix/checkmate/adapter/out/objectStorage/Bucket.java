package com.starmix.checkmate.adapter.out.objectStorage;

import lombok.Getter;

@Getter
public enum Bucket {
    DEFINITION("checkmate-definition"),
    PROJECT("checkmate-project");

    private final String key;

    Bucket(String key) {
        this.key = key;
    }
}