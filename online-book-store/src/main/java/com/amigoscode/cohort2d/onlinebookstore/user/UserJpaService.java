package com.amigoscode.cohort2d.onlinebookstore.user;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("user-jpa")
public class UserJpaService implements UserDao{

    private final UserRepository userRepository;

    public UserJpaService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean existUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
