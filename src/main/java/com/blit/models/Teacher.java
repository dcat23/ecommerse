package com.blit.models;

public class Teacher extends User {

    public Teacher (String name, String email) {
        super(Type.TEACHER, name, email);
    }
}
