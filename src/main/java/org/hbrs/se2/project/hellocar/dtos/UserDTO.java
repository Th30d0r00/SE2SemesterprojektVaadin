package org.hbrs.se2.project.hellocar.dtos;

import org.hbrs.se2.project.hellocar.entities.Rolle;
import org.hbrs.se2.project.hellocar.util.AccountType;

import java.time.LocalDate;
import java.util.List;

public interface UserDTO {

    //General User
    public int getId();
    public String getUserId();
    public String getEmail();
    public String getPassword();
    public List<RolleDTO> getRoles();
    public AccountType getAccountType();

    //Company
    public String getCompanyName();
    public LocalDate getFoundingDate();
    public int getEmployees();

    //Student
    public String getLastname();
    public String getFirstname();
    public LocalDate getBirthday();
    public int getAge();
}

