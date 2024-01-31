package com.blit.models;

public class Student extends User {

    public Student() {
        super(Type.STUDENT);
    }

    public Student(String name, String email) {
        super(Type.STUDENT, name, email);
    }
}
