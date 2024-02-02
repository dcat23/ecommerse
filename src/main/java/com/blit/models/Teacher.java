package com.blit.models;

import com.blit.repositories.CourseRepo;

import java.util.List;

public class Teacher extends User {

    public Teacher() {
        super(Type.TEACHER);
    }

    public Teacher (String name, String email) {
        super(Type.TEACHER, name, email);
    }

    public void addCourse(String name) {
        CourseRepo.insert(new NewCourse(name, getId()));
    }


    @Override
    public List<Course> getCourses() {
        return CourseRepo.byTeacher(getId());
    }
}
