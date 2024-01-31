package com.blit.models;

public abstract class User {

    public enum Type {TEACHER, STUDENT, GUEST}

    private final Type type;
    private String name;
    private String email;

    private String password;

    public User() {
        this.type = Type.GUEST;
        this.name = "guest";
    }


    public User(Type type, String name, String email, String password) {
        this.type = type;
        this.name = name;
        this.email = email;
        this.password = password;
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
