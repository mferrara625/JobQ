package com.mferrara.jobs.repositories;

import com.mferrara.jobs.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username = ?1")
    Optional<User> findUserByName(String name);
}
