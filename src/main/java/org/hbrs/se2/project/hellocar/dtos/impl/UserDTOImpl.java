package org.hbrs.se2.project.hellocar.dtos.impl;

import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.util.AccountType;

import java.util.List;

public class UserDTOImpl implements UserDTO {

    //General User
    private int id;
    private byte[] salt;
    private byte[] hashValue;
    private String userid;
    private String email;
    private String password;
    private List<RolleDTO> roles;
    private AccountType accountType;
    private StudentDTO student;
    private CompanyDTO company;

    @Override
    public String toString() {
        return "UserDTOImpl{" +
                "id=" + id +
                "email: " + email +
                ", roles=" + roles +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public byte[] getSalt() {
        return new byte[0];
    }

    @Override
    public byte[] getHashValue() {
        return new byte[0];
    }

    @Override
    public String getUserId() {
        return userid;
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
    public List<RolleDTO> getRoles() {
        return roles;
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

    }

    @Override
    public void setHashValue(byte[] hash) {

    }

    @Override
    public void setUserId(String userId) {
        this.userid = userId;
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
    public void setRoles(List<RolleDTO> roles) {
        this.roles = roles;
    }
}

