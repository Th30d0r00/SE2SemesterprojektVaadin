package org.hbrs.se2.project.hellocar.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;

import java.time.LocalDate;

public class Company extends User{
    private String companyName;
    private LocalDate foundingDate;
    private int employees;

    @Basic
    @Column(name = "companyName")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "foundingDate")
    public LocalDate getFoundingDate() {
        return foundingDate;
    }

    public void setFoundingDate(LocalDate foundingDate) {
        this.foundingDate = foundingDate;
    }

    @Basic
    @Column(name = "employees")
    public int getEmployees() {
        return employees;
    }

    public void setEmployees(int employees) {
        this.employees = employees;
    }
}

