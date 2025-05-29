package org.demo.student_management.services.interfaces;

import org.demo.student_management.entities.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentServiceInt {
    public Student saveStudent(Student student);
    public Optional<Student> getStudentById(UUID id);
    public Optional<Student> getStudentByEmail(String email);
    public Student getStudentByUserName(String userName);
    public Student updateStudent(Student student);
    public List<Student> getAllStudents();
    public boolean deleteStudent(UUID id);
    
    // Profile picture methods
    public Student updateProfilePicture(UUID id, String profilePictureUrl);
    public String getProfilePictureUrl(UUID id);
}
