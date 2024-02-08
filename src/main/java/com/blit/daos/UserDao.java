package com.blit.daos;

import com.blit.dto.UserRegistration;
import com.blit.models.*;
import com.blit.utils.ConnectionUtil;

import java.sql.*;
import java.util.Base64;

public class UserDao {

    public static User byEmail(String email) {
        try(Connection conn = ConnectionUtil.getConnection())
        {
            String sql = "SELECT * FROM users WHERE email = '" + email +"';";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next())
            {
                User.Type type = User.Type.valueOf(result.getString("user_type"));

                var user = switch(type) {
                    case TEACHER -> new Teacher();
                    case STUDENT -> new Student();
                    default -> new Guest();
                };

                user.setId(result.getInt("user_id"));
                user.setName(result.getString("name"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password"));
                return user;
            }

            return new Guest();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static User all() {
        try(Connection conn = ConnectionUtil.getConnection())
        {
            String sql = "SELECT * FROM users;";
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            if (result.next())
            {
                User.Type type = User.Type.valueOf(result.getString("user_type"));
                User user = switch(type) {
                    case TEACHER -> new Teacher();
                    case STUDENT -> new Student();
                    default -> new Guest();
                };
                user.setId(result.getInt("id"));
                user.setName(result.getString("name"));
                user.setEmail(result.getString("email"));
                return user;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return new Guest();
    }

    public static boolean insert(UserRegistration newUser) {
        try(Connection conn = ConnectionUtil.getConnection())
        {
            String sql = "INSERT INTO users (user_type,name,email,password)"
                + " VALUES (?,?,?,?);";
            PreparedStatement statement = conn.prepareStatement(sql);

            int count = 0;
            statement.setString(++count, newUser.userType());
            statement.setString(++count, newUser.name());
            statement.setString(++count, newUser.email());
            statement.setString(++count, encode(newUser.password()));

            statement.execute();

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encode(String passwordToHash) {
        return new String(Base64.getEncoder()
                .encode(passwordToHash.trim().getBytes()));
    }
}
