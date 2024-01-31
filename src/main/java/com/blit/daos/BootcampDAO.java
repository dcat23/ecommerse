package com.blit.daos;

import com.blit.exceptions.EmailExistsException;
import com.blit.models.User;

public interface BootcampDAO {

    User getUser();

    /**
     * Creates a new user
     *
     */
    boolean register(User user) throws EmailExistsException;

    /**
     * Checks if the credentials are valid
     * then sets the current user
     *
     * @param username
     * @param encryptedPassword
     * @return boolean
     */
    boolean authenticate(String username, String encryptedPassword);
}
