package com.amigoscode.cohort2d.onlinebookstore.user;

import com.amigoscode.cohort2d.onlinebookstore.exceptions.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserObsUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    public UserObsUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws ResourceNotFoundException {
        return userDao.getUserByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Username " + username + " not found"));
    }
}
