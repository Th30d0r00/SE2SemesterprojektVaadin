package org.hbrs.se2.project.hellocar.dtos;

import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.entities.Rolle;
import org.hbrs.se2.project.hellocar.entities.Student;
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
    public StudentDTO getStudent();
    public CompanyDTO getCompany();

    public void setId(int id);
    public void setUserId(String userId);
    public void setEmail(String email);
    public void setPassword(String password);
    public void setRoles(List<RolleDTO> roles);
    public void setAccountType(AccountType accountType);
    public void setStudent(StudentDTO student);
    public void setCompany(CompanyDTO company);
}

