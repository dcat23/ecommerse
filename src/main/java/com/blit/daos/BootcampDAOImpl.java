package com.blit.daos;

import com.blit.exceptions.EmailExistsException;
import com.blit.models.*;
import com.blit.repositories.UserRepo;
import java.util.ArrayList;
import java.util.List;

public class BootcampDAOImpl implements BootcampDAO {

    private User user;

    public BootcampDAOImpl() {
        this.user = new Guest();
    }

    @Override
    public boolean register(NewUser newUser) throws EmailExistsException {

        if (UserRepo.byEmail(newUser.email()).exists())
        {
            throw new EmailExistsException(newUser.email() + " already exists");
        }



        User.Type type = User.Type.valueOf(newUser.userType());

        UserRepo.insert(newUser);


        return true;
    }

    @Override
    public boolean authenticate(String email, String password)
    {
//        exceptions : UserNotFound, InvalidCredentials

        System.out.println("encrypted password: " + password);

        return false;
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
