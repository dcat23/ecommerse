package com.blit.models;

public class User {

    public enum Type {TEACHER, STUDENT, GUEST}

    private final Type type;
    private String name;
    private String email;

    public User() {
        this.type = Type.GUEST;
        this.name = "guest";
    }

    public User(Type type, String name, String email) {
        this.type = type;
        this.name = name;
        this.email = email;
    }

    public Type getType() {
        return type;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean loggedIn() { return type != Type.GUEST; }
}
