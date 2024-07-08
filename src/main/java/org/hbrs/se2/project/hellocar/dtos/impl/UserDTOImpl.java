package org.hbrs.se2.project.hellocar.dtos.impl;

import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.util.AccountType;

public class UserDTOImpl implements UserDTO {

    //General User
    private int id;
    private byte[] salt;
    private byte[] hashValue;
    private String email;
    private String password;
    private String role;
    private AccountType accountType;
    private StudentDTO student;
    private CompanyDTO company;

    @Override
    public String toString() {
        return "UserDTOImpl{" +
                "id=" + id +
                "email: " + email +
                ", role=" + role +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public byte[] getSalt() {
        return salt;
    }

    @Override
    public byte[] getHashValue() {
        return hashValue;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public AccountType getAccountType() {
        return accountType;
    }

    @Override
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public StudentDTO getStudent() {
        return student;
    }

    @Override
    public CompanyDTO getCompany() {
        return company;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    @Override
    public void setHashValue(byte[] hash) {
        this.hashValue = hash;
    }

    @Override
    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    @Override
    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public void setRole(String role) {
        this.role = role;
    }
}

