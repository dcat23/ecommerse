package com.blit.models;

import com.blit.dto.UserRegistration;
import com.blit.utils.ConnectionUtil;
import org.junit.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.blit.daos.UserDao.encode;
import static com.blit.models.User.Type.STUDENT;
import static com.blit.models.User.Type.TEACHER;
import static org.junit.Assert.*;

public class UserTest {

    private UserRegistration testTeacher;
    private UserRegistration testStudent;

    @BeforeClass
    public static void beforeAll() {
    }

    @Before
    public void setUp() throws Exception {
        final String teacherType = "TEACHER";
        final String studentType = "STUDENT";

        final String password1 = encode("password-one");
        final String password2 = encode("password-two");

        final String teacherName1 = "Mrs. Handy";
        final String teacherEmail = "teach@school.org";

        final String studentName1 = "Student Name";
        final String studentEmail = "student@personal.com";

        testTeacher = new UserRegistration(teacherType, teacherName1, teacherEmail, password1);
        testStudent = new UserRegistration(studentType, studentName1, studentEmail, password2);
    }

    @After
    public void tearDown() throws Exception {
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
    public void createStudent() {
        Student s = new Student();
        s.setEmail(testStudent.email());
        s.setName(testStudent.name());
        s.setPassword(testStudent.password());

        System.out.println(s);
        assertEquals(s.getType(), STUDENT);
    }
    @Test
    public void createTeacher() {
        Teacher t = new Teacher();
        t.setEmail(testTeacher.email());
        t.setName(testTeacher.name());
        t.setPassword(testTeacher.password());

        System.out.println(t);
        assertEquals(t.getType(), TEACHER);
    }
    @Test @Ignore
    public void getCourses() {
    }

    @Test @Ignore
    public void exists() {
    }

    @Test @Ignore
    public void addCourse() {
    }
}