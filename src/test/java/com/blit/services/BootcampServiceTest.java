package com.blit.services;

import com.blit.daos.CourseDao;
import com.blit.daos.UserDao;
import com.blit.dto.CourseCreation;
import com.blit.dto.UserRegistration;
import com.blit.exceptions.EmailExistsException;
import com.blit.exceptions.InvalidCredentialsException;
import com.blit.exceptions.UserNotFoundException;
import com.blit.models.*;
import com.blit.utils.ConnectionUtil;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.blit.daos.UserDao.encode;
import static org.junit.Assert.*;

public class BootcampServiceTest {

    private BootcampService service;
    private UserRegistration testTeacher;
    private UserRegistration testStudent;

    private static final String testCourseName = "How to train your dragon";

    @BeforeClass
    public static void beforeClass() {
    }
    @AfterClass
    public static void afterClass() {
    }
    @Before
    public void before() {
        service = new BootcampServiceImpl();

        final String teacherType = "TEACHER";
        final String studentType = "STUDENT";

        final String password1 = "password-one";
        final String password2 = "password-two";

        final String teacherName1 = "Mrs. Handy";
        final String teacherEmail = "teach@school.org";

        final String studentName1 = "Student Name";
        final String studentEmail = "student@personal.com";

        testTeacher = new UserRegistration(teacherType, teacherName1, teacherEmail, password1);
        testStudent = new UserRegistration(studentType, studentName1, studentEmail, password2);

    }
    @After
    public void after() {
        try (Connection conn = ConnectionUtil.getConnection()) {
            assertNotNull(conn);
            Statement statement = conn.createStatement();
            statement.execute("TRUNCATE TABLE enrollments");
            statement.execute("SET FOREIGN_KEY_CHECKS = 0");
            statement.execute("TRUNCATE TABLE courses");
            statement.execute("TRUNCATE TABLE users");
            statement.execute("SET FOREIGN_KEY_CHECKS = 1");
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void registerTeacher() {
        try {
            boolean registered = service.register(testTeacher);
            assertTrue(registered);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void registerStudent() {
        try {
            boolean registered = service.register(testStudent);
            assertTrue(registered);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void registerDuplicate() {

        try {
            boolean registered = service.register(testStudent);
            assertTrue(registered);

            assertThrows(EmailExistsException.class, () -> { service.register(testStudent); });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void authenticate() {

        try {
            boolean inserted = UserDao.insert(testTeacher);
            assertTrue(inserted);

            boolean authenticated = service.authenticate(testTeacher.email(), testTeacher.password());
            assertTrue(authenticated);
        } catch (UserNotFoundException | InvalidCredentialsException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void authenticateBadEmail() {

        assertThrows(UserNotFoundException.class, () -> {
            service.authenticate("bad@email.com", testTeacher.password());
        });
    }
    @Test
    public void authenticateWrongPassword() {

        boolean inserted = UserDao.insert(testTeacher);
        assertTrue(inserted);

        assertThrows(InvalidCredentialsException.class, () -> {
            service.authenticate(testTeacher.email(), "wrong password");
        });
    }

    @Test
    public void getCourses() {
        UserDao.insert(testTeacher);
        Teacher teacher = (Teacher) UserDao.byEmail(testTeacher.email());
        assertEquals(teacher.getCourses().size(), 0);

        teacher.addCourse(testCourseName);

        List<Course> courses = teacher.getCourses();
        assertEquals(courses.size(), 1);

        Course first = courses.get(0);

        assertEquals(first.getName(), testCourseName);

    }

    @Test
    public void getEnrolledCourses() {
        UserDao.insert(testStudent);
        UserDao.insert(testTeacher);
        Teacher teacher = (Teacher) UserDao.byEmail(testTeacher.email());
        Student student = (Student) UserDao.byEmail(testStudent.email());
        teacher.addCourse(testCourseName);

        assertEquals(student.getCourses().size(), 0);

        student.enroll(testCourseName);

        List<Course> courses = student.getCourses();
        assertEquals(courses.size(), 1);

        Course first = courses.get(0);

        assertEquals(first.getName(), testCourseName);
    }
}