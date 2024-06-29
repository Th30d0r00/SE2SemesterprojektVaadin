package org.hbrs.se2.project.hellocar.dtos.impl;

import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;

import java.time.LocalDate;

public class CompanyDTOImpl implements CompanyDTO {

    private int id;
    private String companyName;
    private LocalDate foundingDate;
    private int employees;
    private String locations;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public LocalDate getFoundingDate() {
        return foundingDate;
    }

    @Override
    public void setFoundingDate(LocalDate foundingDate) {
        this.foundingDate = foundingDate;
    }

    @Override
    public int getEmployees() {
        return employees;
    }

    @Override
    public void setEmployees(int employees) {
        this.employees = employees;
    }

    @Override
    public String getLocations() {
        return locations;
    }

    @Override
    public void setLocations(String locations) {
        this.locations = locations;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

}
