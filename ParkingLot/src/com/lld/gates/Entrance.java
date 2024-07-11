package com.lld.gates;

import java.util.UUID;

public class Entrance {
    private String id;

    public Entrance() {
        this.id = UUID.randomUUID().toString();
    }
}
