package org.demo.student_management.services.interfaces;

import org.demo.student_management.entities.User;
import java.util.List;
import java.util.UUID;

public interface UserServiceInt {
    User createUser(User user);
    User updateUser(UUID id, User user);
    boolean deleteUser(UUID id);
    User getUserById(UUID id);
    List<User> getAllUsers();
    User getUserByEmail(String email);
    User login(String email, String password);
}
