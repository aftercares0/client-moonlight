package com.moonlight.client.module.api;

public enum Category {

    COMBAT("Combat", 0), MOVEMENT("Movement", 1), PLAYER("Player", 2), VISUALS("Visuals", 3) , EXPLOIT("Exploit", 4),
    OTHERS("Others", 5);

    private String name;
    private int id;

    Category(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

}