package com.amigoscode.cohort2d.onlinebookstore.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {

    boolean existsUserByEmail(String email);

    boolean existsUserById(Long id);
}
