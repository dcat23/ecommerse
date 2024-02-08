package com.blit.services;

import com.blit.exceptions.EmailExistsException;
import com.blit.exceptions.InvalidCredentialsException;
import com.blit.exceptions.InvalidEmailFormatException;
import com.blit.exceptions.UserNotFoundException;
import com.blit.models.*;
import com.blit.daos.UserDao;
import com.blit.utils.Validation;

import java.util.ArrayList;
import java.util.List;

public class BootcampServiceImpl implements BootcampService {

    private User user;

    public BootcampServiceImpl() {
        this.user = new Guest();
    }

    @Override
    public boolean register(NewUser newUser) throws Exception {

        if (UserDao.byEmail(newUser.email()).exists())
        {
            throw new EmailExistsException(newUser.email() + " already exists");
        }

        if (!Validation.checkEmail(newUser.email()))
        {
            throw new InvalidEmailFormatException("Email " + newUser.email() + " should have format: NAME@HOST.COM");
        }

        User.Type type = User.Type.valueOf(newUser.userType());

        if (type == User.Type.GUEST)
        {
            throw new Exception("Cannot register as " + type.name());
        }

        UserDao.insert(newUser);

        user = UserDao.byEmail(newUser.email());

        return true;
    }

    @Override
    public boolean authenticate(String email, String password) throws UserNotFoundException, InvalidCredentialsException {

        User user = UserDao.byEmail(email);
        if (!user.exists())
        {
            throw new UserNotFoundException("No user with email "+ email);
        }

        String encrypted = UserDao.encode(password);

        if (!encrypted.equals(user.getPassword()))
        {
            throw new InvalidCredentialsException("Incorrect password");
        }


        this.user = user;

        return true;
    }

    @Override
    public void resetUser() {
        this.user = new Guest();
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public List<Course> getCourses() {

        List<Course> courses = new ArrayList<>();


        return courses;
    }

    @Override
    public List<Course> getEnrolledCourses() {

        if (user instanceof Guest) return getCourses();

        List<Course> courses = new ArrayList<>();



        return courses;
    }

}
