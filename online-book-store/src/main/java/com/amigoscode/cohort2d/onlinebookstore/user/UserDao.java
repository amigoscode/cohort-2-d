package com.amigoscode.cohort2d.onlinebookstore.user;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    User saveUser(User user);

    List<User> getUsers();
    boolean existUserByEmail(String email);

    boolean existUserById(Long id);

    Optional<User> getUserById(Long id);
}
