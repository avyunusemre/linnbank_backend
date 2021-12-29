package com.linnbankbackend.demo.repository;

import com.linnbankbackend.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {

    @Override
    Optional<Role> findById(Long id);

    Role findByRoleName(String roleName);
}
