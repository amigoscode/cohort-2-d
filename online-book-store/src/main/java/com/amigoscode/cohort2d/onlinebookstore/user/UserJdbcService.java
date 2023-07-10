package com.amigoscode.cohort2d.onlinebookstore.user;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("user-jdbc")
public class UserJdbcService implements UserDao{
    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public List<User> getUsers() {
        return null;
    }

    @Override
    public boolean existUserByEmail(String email) {
        return false;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return Optional.empty();
    }
}
