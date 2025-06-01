package org.demo.student_management.services.interfaces;

import org.demo.student_management.dto.ChangePasswordRequest;
import org.demo.student_management.dto.KeycloakTokenResponse;
import org.demo.student_management.dto.RegisterRequest;
import org.demo.student_management.entities.User;
import java.util.List;
import java.util.UUID;

public interface UserServiceInt {
    User createUser(User user);
    User updateUser(UUID id, User user);
    boolean deleteUser(UUID id);
    User getUserById(UUID id);
    User getUserByUserName(String userName);
    List<User> getAllUsers();
    User getUserByEmail(String email);
    User login(String email, String password);
    KeycloakTokenResponse loginWithKeycloak(String username, String password) throws Exception;
    void changePassword(String username, ChangePasswordRequest request) throws Exception;
    public void registerUserInKeycloak(RegisterRequest request) throws Exception;
}
