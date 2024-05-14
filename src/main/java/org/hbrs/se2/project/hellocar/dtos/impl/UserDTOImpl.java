package org.hbrs.se2.project.hellocar.dtos.impl;

import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.util.AccountType;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public class UserDTOImpl implements UserDTO {

    //General User
    private int id;
    private String userid;
    private String email;
    private String password;
    private List<RolleDTO> roles;
    private AccountType accountType;

    //Company
    private String companyName;
    private LocalDate foundingDate;
    private int employees;

    //Student
    private String firstname;
    private String lastname;
    private LocalDate birthday;
    private int age;

    @Override
    public String toString() {
        return "UserDTOImpl{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", roles=" + roles +
                '}';
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getUserId() {
        return userid;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getPassword() {
        return password;
    }

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

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public LocalDate getFoundingDate() {
        return foundingDate;
    }

    public void setFoundingDate(LocalDate foundingDate) {
        this.foundingDate = foundingDate;
    }

    @Override
    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public int getAge() {
        return age;
    }

    public void setId(int userId) {
        this.id = userId;
    }


    public void setRoles(List<RolleDTO> roles) {
        this.roles = roles;
    }
}

