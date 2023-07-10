package com.amigoscode.cohort2d.onlinebookstore.user;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User createUser(User user);

    List<User> getUsers();
    boolean existUserByEmail(String email);

    Optional<User> getUserById(Long id);
}
