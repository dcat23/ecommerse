package com.blit.daos;

import com.blit.exceptions.InvalidCredentialsException;
import com.blit.exceptions.UserNotFoundException;
import com.blit.models.Course;
import com.blit.models.NewUser;
import com.blit.models.User;

import java.util.List;

public interface BootcampDAO {

    User getUser();
    List<Course> getCourses();
    List<Course> getEnrolledCourses();

    /**
     * Creates a new user
     */
    boolean register(NewUser newUser) throws Exception;

    /**
     * Checks if the credentials are valid
     *
     * @param email must be unique
     * @param password will be encrypted here
     * @return boolean
     */
    boolean authenticate(
            String email,
            String password
    ) throws UserNotFoundException,
            InvalidCredentialsException;

    /**
     * Sets the current user to Guest;
     *  effectively logs out
     */
    void resetUser();
}
