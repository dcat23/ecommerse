package com.blit.models;

public class Teacher extends User {

    public Teacher() {
        super(Type.TEACHER);
    }

    public Teacher (String name, String email) {
        super(Type.TEACHER, name, email);
    }
}
