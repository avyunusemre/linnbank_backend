package com.linnbankbackend.demo.repository;

import com.linnbankbackend.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {

    @Override
    Optional<User> findById(Long userId);

    Optional<User> findBySsn(String ssn);

    Boolean existsByEmail(String email);

    Boolean existsBySsn(String ssn);
}
