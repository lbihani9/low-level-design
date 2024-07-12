package com.lld.account;

import java.util.UUID;

public abstract class Account {
    private String id;
    private String name;
    private String password;
    private String email;

    Account(String name, String password, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void resetPassword(String password) {
        // Not being very secure here since it's just a demo.
        this.password = password;
    }
}
