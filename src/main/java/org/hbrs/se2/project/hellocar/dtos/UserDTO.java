package org.hbrs.se2.project.hellocar.dtos;

import org.hbrs.se2.project.hellocar.util.AccountType;

public interface UserDTO {

    //General User
    public int getId();
    public byte[] getSalt();
    public byte[] getHashValue();
    public String getEmail();
    public String getPassword();
    public String getRole();
    public AccountType getAccountType();
    public StudentDTO getStudent();
    public CompanyDTO getCompany();

    public void setId(int id);
    public void setSalt(byte[] salt);
    public void setHashValue(byte[] hash);
    public void setEmail(String email);
    public void setPassword(String password);
    public void setRole(String role);
    public void setAccountType(AccountType accountType);
    public void setStudent(StudentDTO student);
    public void setCompany(CompanyDTO company);
}

