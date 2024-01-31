package com.blit.models;

public class Teacher extends User {

    public Teacher() {
    }

    public Teacher (String name, String email, String password) {
        super(Type.TEACHER, name, email, password);
    }
}
