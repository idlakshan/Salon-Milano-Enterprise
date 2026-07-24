package com.milano.repo;

import com.milano.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;
import java.util.UUID;

@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User, UUID> {

    @Query(value = "SELECT * FROM users WHERE fullName LIKE ?1", nativeQuery = true)
    Page<User> findAllUsers(String searchText, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM users WHERE fullName LIKE ?1", nativeQuery = true)
    long countAllUsers(String searchText);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
