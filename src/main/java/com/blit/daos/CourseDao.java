package com.blit.daos;

import com.blit.models.*;
import com.blit.utils.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    public static List<Course> byTeacher(int teacherId) {
        List<Course> courses = new ArrayList<>();
        try(Connection conn = ConnectionUtil.getConnection())
        {
            String sql = "SELECT * FROM Courses WHERE teacher_id = '" + teacherId +"';";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next())
            {
                Course course = new Course();
                course.setId(result.getInt("course_id"));
                course.setName(result.getString("course_name"));
                course.setTeacherId(teacherId);
                courses.add(course);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return courses;
    }
    public static List<Course> withStudent(int studentId) {
        List<Course> courses = new ArrayList<>();
        try(Connection conn = ConnectionUtil.getConnection())
        {
            String sql = "SELECT Students.student_id, Students.student_name, Courses.course_id, Courses.course_name" +
                    " FROM Students JOIN Courses" +
                    " ON Students.course_id = Course.course_id" +
                    " WHERE Students.student_id = '" + studentId + "';";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next())
            {
                Course course = new Course();
                course.setId(result.getInt("id"));
                course.setName(result.getString("name"));
                course.setTeacherId(result.getInt("teacher_id"));
                courses.add(course);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return courses;
    }
    public static List<Course> all() {
        List<Course> courses = new ArrayList<>();
        try(Connection conn = ConnectionUtil.getConnection())
        {
            String sql = "SELECT * FROM Courses;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next())
            {
                Course course = new Course();
                course.setId(result.getInt("course_id"));
                course.setName(result.getString("course_name"));
                course.setTeacherId(result.getInt("teacher_id"));
                courses.add(course);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return courses;
    }

    public static void insert(NewCourse newCourse) {
        try(Connection conn = ConnectionUtil.getConnection())
        {
            String sql = "INSERT INTO Courses (teacher_id,course_name)"
                    + " VALUES (?,?);";
            PreparedStatement statement = conn.prepareStatement(sql);

            int count = 0;
            statement.setInt(++count, newCourse.teacherId());
            statement.setString(++count, newCourse.name());

            statement.execute();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void enroll(int courseId, Student student) {
        try(Connection conn = ConnectionUtil.getConnection())
        {
            String sql = "INSERT INTO Students (student_id, student_name, course_id)" +
                    " VALUES (?,?,?);";
            PreparedStatement statement = conn.prepareStatement(sql);

            int count = 0;
            statement.setInt(++count, student.getId());
            statement.setString(++count, student.getName());
            statement.setInt(++count, courseId);

            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
