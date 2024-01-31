package com.blit.utils;

import com.blit.models.User;
import org.junit.*;

import java.sql.*;

import static org.junit.Assert.*;

public class ConnectionUtilTest {

    @BeforeClass
    public static void beforeClass() {
        try (Connection conn = ConnectionUtil.getConnection()) {
            assertNotNull(conn);
            String sql = "TRUNCATE TABLE users;";
            Statement statement = conn.createStatement();
            statement.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Before @Ignore
    public void before() {
        try (Connection conn = ConnectionUtil.getConnection()) {
            assertNotNull(conn);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void afterClass() {
        try (Connection conn = ConnectionUtil.getConnection()) {
            assertNotNull(conn);
            String sql = "TRUNCATE TABLE users";
            Statement statement = conn.createStatement();
            statement.execute(sql);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void after() {
        try (Connection conn = ConnectionUtil.getConnection()) {
            assertNotNull(conn);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createConnection()
    {
        try (Connection conn = ConnectionUtil.getConnection()) {
            assertNotNull(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String TEST_EMAIL = "teacher@mail.org";
    private static String TEST_NAME = "Mrs. Teacher";
    private static String TEST_PASSWORD = "unencryptedpassword";
    private static User.Type TEST_TYPE = User.Type.TEACHER;


    @Test
    public void insert() {
        try(Connection conn = ConnectionUtil.getConnection())
        {
            Statement statement = conn.createStatement();
            String countSQl = "SELECT COUNT(*) FROM users;";

            ResultSet countStatement = statement.executeQuery(countSQl);

            int initialCount = 0;
            if (countStatement.next())
            {
                initialCount = countStatement.getInt(1);

            }

            String sql = "INSERT INTO users (user_type,name,email,password)"
                    + " VALUES (?,?,?,?);";
            PreparedStatement prepared = conn.prepareStatement(sql);

            int count = 0;
            prepared.setString(++count, TEST_TYPE.toString());
            prepared.setString(++count, TEST_NAME);
            prepared.setString(++count, TEST_EMAIL);
            prepared.setString(++count, TEST_PASSWORD);
            prepared.execute();

            countStatement = statement.executeQuery(countSQl);

            int finalCount = 0;
            if (countStatement.next())
            {
                finalCount = countStatement.getInt(1);

            }

            assertEquals(1, (finalCount-initialCount));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void selectUsers() {
        try(Connection conn = ConnectionUtil.getConnection())
        {
            String sql = "SELECT * FROM users WHERE email = '" + TEST_EMAIL + "';";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            if (result.next())
            {
                String tableEmail = result.getString("email");
                assertEquals(TEST_EMAIL, tableEmail);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
