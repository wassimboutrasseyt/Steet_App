package org.demo.core_app;

import org.demo.core_app.entities.Student;
import org.demo.core_app.entities.enums.Interests;
import org.demo.core_app.entities.enums.Majors;
import org.demo.core_app.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner init(StudentRepository studentRepository) {
        return args -> {

//            studentRepository.save(Student.builder()..build());
//
//            Student s1 =  new Student("John", "Doe", "john.doe", "john.doe@email.com", "password123", Majors.COMPUTER_SCIENCE, LocalDate.of(1995, 5, 15), List.of(Interests.ART, Interests.CHEMISTRY));
//            Student s2 =new Student("Jane", "Smith", "jane.smith", "jane.smith@email.com", "password123", Majors.MATHEMATICS, LocalDate.of(1998, 8, 22), List.of(Interests.ART, Interests.HISTORY));
//            Student s3 = new Student("Alice", "Johnson", "alice.johnson", "alice.johnson@email.com", "password123", Majors.PHYSICS, LocalDate.of(2000, 3, 10), List.of(Interests.BIOLOGY, Interests.MATH));
//
//            studentRepository.saveAll(List.of(
//                  s1,
//                    s2,
//                    s3
//            ));

            Student student1 = new Student("John", "Doe", "john.doe", "john.doe@email.com", "password123", Majors.COMPUTER_SCIENCE, LocalDate.of(1995, 5, 15), List.of(Interests.ART, Interests.CHEMISTRY));
            Student student2 = new Student("Jane", "Smith", "jane.smith", "jane.smith@email.com", "password123", Majors.MATHEMATICS, LocalDate.of(1998, 8, 22), List.of(Interests.ART, Interests.HISTORY));
            Student student3 = new Student("Alice", "Johnson", "alice.johnson", "alice.johnson@email.com", "password123", Majors.PHYSICS, LocalDate.of(2000, 3, 10), List.of(Interests.BIOLOGY, Interests.MATH));

            studentRepository.saveAll(List.of(student1, student2, student3));
            studentRepository.findAll().forEach(student -> {

                System.out.println("==================================================");
                System.out.println("Student: " + student.getFirstName() + " " + student.getLastName());
                System.out.println("Email: " + student.getEmail());
                System.out.println("Major: " + student.getMajor());
                System.out.println("Date of Birth: " + student.getDob());
                System.out.println("Interests: " + student.getInterest());
            });
        };
    }


}
