package com.blit.models;

public abstract class User {

    public enum Type {TEACHER, STUDENT, GUEST}
    private final Type type;
    private int id;
    private String name;
    private String email;

    public User() {
        this.type = Type.GUEST;
        this.name = "Guest";
    }
    public User(Type type) {
        this.type = type;
    }

    public User(Type type, String name, String email) {
        this.type = type;
        this.name = name;
        this.email = email;
    }

    public Type getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean exists() { return type != Type.GUEST; }
}
