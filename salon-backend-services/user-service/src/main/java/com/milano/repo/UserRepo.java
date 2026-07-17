package com.milano.repo;

import com.milano.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {

    @Query(value = "SELECT * FROM user WHERE fullName LIKE ?1", nativeQuery = true)
    public Page<User> findAllUsers(String searchText, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM user WHERE fullName LIKE ?1", nativeQuery = true)
    public long countAllUsers(String searchText);

}
