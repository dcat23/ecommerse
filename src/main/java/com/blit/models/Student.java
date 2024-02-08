package com.blit.models;

import com.blit.daos.CourseDao;

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

        boolean enrolled = CourseDao.enroll(course, this);
        if (enrolled)
        {
            courseId = course.getId();
            System.out.println("enrolled into " + course.getName());
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                '}';
    }
}
