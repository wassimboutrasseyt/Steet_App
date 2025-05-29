package org.demo.student_management.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Administrator extends User {

    public Administrator(String firstName, String lastName, String username, String email, String password) {
        super(null, firstName, lastName, username, email, password,null);
    }
}