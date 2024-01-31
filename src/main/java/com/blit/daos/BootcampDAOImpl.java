package com.blit.daos;

import com.blit.exceptions.EmailExistsException;
import com.blit.models.Guest;
import com.blit.models.User;

public class BootcampDAOImpl implements BootcampDAO {

    private User user;

    public BootcampDAOImpl() {
        this.user = new Guest();
    }

    public User getUserByEmail(String email) {

        return null;
    }
    @Override
    public boolean register(User newUser) throws EmailExistsException {

        if (getUserByEmail(newUser.getEmail()) != null)
        {
            throw new EmailExistsException(newUser.getEmail() + " already exists");
        }

        this.user = newUser;

        return true;
    }

    @Override
    public boolean authenticate(String username, String encryptedPassword)
    {
//        exceptions : UserNotFound, InvalidCredentials

        System.out.println("encrypted password: " + encryptedPassword);

        return false;
    }

    @Override
    public User getUser() {
        return user;
    }

}
