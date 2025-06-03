package org.demo.student_management.entities;

import java.util.UUID;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Administrator extends User {

    public Administrator(UUID id,  String firstName, String lastName, String username, String email, String password) {
        super(id, firstName, lastName, username, email, password,null);
    }
}