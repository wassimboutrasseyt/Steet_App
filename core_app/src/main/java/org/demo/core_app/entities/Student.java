package org.demo.core_app.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.demo.core_app.entities.enums.Interests;
import org.demo.core_app.entities.enums.Majors;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
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
    @OneToMany(mappedBy = "participant")
    private List<Participation> participations;

    // memberships of the student on private rooms
    @OneToMany(mappedBy = "student")
    private List<Membership> memberships;

    // Constructor to initialize User and Student attributes
    public Student(String firstName, String lastName, String username, String email, String password, Majors major, LocalDate dob, List<Interests> interest) {
        super(null, firstName, lastName, username, email, password); // Pass null for id, it will be generated
        this.major = major;
        this.dob = dob;
        this.interest = interest;
    }
}
