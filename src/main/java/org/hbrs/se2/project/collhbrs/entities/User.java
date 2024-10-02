package org.hbrs.se2.project.collhbrs.entities;

import org.hbrs.se2.project.collhbrs.util.AccountType;

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
