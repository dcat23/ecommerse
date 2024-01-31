package com.blit.utils;

import org.junit.Ignore;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ConnectionUtilTest {

    @Test @Ignore
    public void createConnection()
    {
        try (Connection conn = ConnectionUtil.getConnection()) {
            assertNotNull(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void executesQuery() {
        try(Connection conn = ConnectionUtil.getConnection())
        {
            String sql = "SELECT * FROM users;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            assertTrue(result.next());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void makesCall() {
        try(Connection conn = ConnectionUtil.getConnection())
        {
            String sql = "SELECT * FROM users;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            assertTrue(result.next());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
