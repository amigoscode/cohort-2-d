package com.amigoscode.cohort2d.onlinebookstore.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {

    boolean existsUserByEmail(String email);
}
