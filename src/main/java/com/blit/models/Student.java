package com.blit.models;

public class Student extends User {

    public Student() {
    }

    public Student(String name, String email, String password) {
        super(Type.STUDENT, name, email, password);
    }
}
