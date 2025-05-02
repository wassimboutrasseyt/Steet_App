package org.demo.student_management.services.interfaces;

import org.demo.student_management.entities.Administrator;
import java.util.List;
import java.util.UUID;

public interface AdminServiceInt {
    Administrator createAdministrator(Administrator admin);
    Administrator updateAdministrator(UUID id, Administrator admin);
    boolean deleteAdministrator(UUID id);
    Administrator getAdministratorById(UUID id);
    List<Administrator> getAllAdministrators();

}
