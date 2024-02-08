package com.blit.models;

import com.blit.daos.CourseDao;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private int courseId;

    public Student() {
        super(Type.STUDENT);
    }

    public Student(String name, String email) {
        super(Type.STUDENT, name, email);
    }

    @Override
    public List<Course> getCourses() {

        return CourseDao.withStudent(courseId);
    }

    public void enroll(String courseName) {
        Course course = CourseDao.byName(courseName);

        if (course == null) return;

        System.out.println("enroll " + course);
        courseId = course.getId();
    }

    @Override
    public String toString() {
        return "Student{" +
                "courseId=" + courseId +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
