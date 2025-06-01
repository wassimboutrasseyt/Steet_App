package org.demo.student_management.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

import org.demo.student_management.entities.enums.Interests;
import org.demo.student_management.entities.enums.Majors;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User{

    @Enumerated(EnumType.STRING) // Map the enum as a string in the database
    private Majors major;

    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private List<Interests> interest;

    // the participation of the student in public rooms
//    @OneToMany(mappedBy = "participant")
//    private List<Participation> participations;
    private UUID id_participations;

    // memberships of the student on private rooms
//    @OneToMany(mappedBy = "student")
//    private List<Membership> memberships;
    private UUID id_memberships;    // Constructor to initialize User and Student attributes
    public Student(String firstName, String lastName, String username, String email, String password, Majors major, LocalDate dob, List<Interests> interest, String profilePictureUrl) {
        super(null, firstName, lastName, username, email, password, profilePictureUrl); // Include profilePictureUrl
        this.major = major;
        this.dob = dob;
        this.interest = interest;
    }
}
