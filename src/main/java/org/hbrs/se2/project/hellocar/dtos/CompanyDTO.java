package org.hbrs.se2.project.hellocar.dtos;

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

    public String getStandorte();

    void setStandorte(String standorte);
}
