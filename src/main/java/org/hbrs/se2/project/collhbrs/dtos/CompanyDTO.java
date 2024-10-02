package org.hbrs.se2.project.collhbrs.dtos;

import java.time.LocalDate;

public interface CompanyDTO {

    int getId();

    void setId(int id);

    public String getCompanyName();

    void setCompanyName(String companyName);

    public LocalDate getFoundingDate();

    void setFoundingDate(LocalDate foundingDate);

    public int getEmployees();

    void setEmployees(int employees);

    public String getLocations();

    void setLocations(String standorte);

    public String getDescription();

    void setDescription(String description);
}
