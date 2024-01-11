package com.example.randlandmanageuser.entity.repository;

import com.example.randlandmanageuser.entity.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);
}
