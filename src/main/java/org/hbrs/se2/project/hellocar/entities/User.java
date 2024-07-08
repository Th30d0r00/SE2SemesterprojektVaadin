package org.hbrs.se2.project.hellocar.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import org.hbrs.se2.project.hellocar.util.AccountType;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class User {
    private int id;
    private String email;
    private String password;
    private String role;
    private AccountType accountType;
    private Student studentenInfos;
    private Company firmenInfos;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public Student getStudentenInfos() {
        return studentenInfos;
    }

    public void setStudentenInfos(Student studentenInfos) {
        this.studentenInfos = studentenInfos;
    }

    public Company getFirmenInfos() {
        return firmenInfos;
    }

    public void setFirmenInfos(Company firmenInfos) {
        this.firmenInfos = firmenInfos;
    }
}
