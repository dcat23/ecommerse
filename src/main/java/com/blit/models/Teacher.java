package com.blit.models;

import com.blit.daos.CourseDao;
import com.blit.dto.CourseCreation;

import java.util.List;

public class Teacher extends User {

    public Teacher() {
        super(Type.TEACHER);
    }

    public Teacher (String name, String email) {
        super(Type.TEACHER, name, email);
    }

    public void addCourse(String name) {
        CourseDao.insert(new CourseCreation(name, getId()));
    }

    @Override
    public List<Course> getCourses() {
        return CourseDao.byTeacher(this);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                "}";
    }
}
