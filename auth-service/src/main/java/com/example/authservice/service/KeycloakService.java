package com.example.authservice.service;

import com.example.authservice.dto.UserRegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public void registerUser(UserRegistrationRequest request) {
        CredentialRepresentation credential = createPasswordCredentials(request.getPassword());
        UserRepresentation user = createUserRepresentation(request, credential);
        
        UsersResource usersResource = keycloak.realm(realm).users();
        Response response = usersResource.create(user);
        
        if (response.getStatus() < 200 || response.getStatus() >= 300) {
            throw new RuntimeException("Failed to register user");
        }
    }

    private CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);
        return credential;
    }

    private UserRepresentation createUserRepresentation(UserRegistrationRequest request, 
                                                      CredentialRepresentation credential) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);
        user.setCredentials(Collections.singletonList(credential));
        return user;
    }
}
