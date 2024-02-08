package com.blit.models;

import com.blit.daos.CourseDao;

import java.util.List;

public class Teacher extends User {

    public Teacher() {
        super(Type.TEACHER);
    }

    public Teacher (String name, String email) {
        super(Type.TEACHER, name, email);
    }

    public void addCourse(String name) {
        CourseDao.insert(new NewCourse(name, getId()));
    }

    @Override
    public List<Course> getCourses() {
        return CourseDao.byTeacher(getId());
    }
}
