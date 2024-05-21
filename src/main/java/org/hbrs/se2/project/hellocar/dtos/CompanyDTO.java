package org.hbrs.se2.project.hellocar.dtos;

import java.time.LocalDate;

public interface CompanyDTO {

    public String getCompanyName();

    void setCompanyName(String companyName);

    public LocalDate getFoundingDate();

    void setFoundingDate(LocalDate foundingDate);

    public int getEmployees();

    void setEmployees(int employees);
}
