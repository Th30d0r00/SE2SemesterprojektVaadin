package org.hbrs.se2.project.hellocar.dtos;


import org.hbrs.se2.project.hellocar.entities.Company;

import java.time.LocalDateTime;

public interface AnzeigeDTO {
    int getId();
    String getJobTitle();
    String getStandort();
    String getJobType();
    String getJobDescription();
    Company getCompany();
    LocalDateTime getPublicationDate();

    void setId(int id);
    void setJobTitle(String jobTitle);
    void setStandort(String standort);
    void setJobType(String jobType);
    void setJobDescription(String JobDescription);
    void setCompany(Company company);
    void setPublicationDate(LocalDateTime publicationDate);
}
