package org.demo.core_app.repositories;

import org.demo.core_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUserName(String userName);
    User findByEmail(String email);
    User findByUserNameOrEmail(String userName, String email);
}