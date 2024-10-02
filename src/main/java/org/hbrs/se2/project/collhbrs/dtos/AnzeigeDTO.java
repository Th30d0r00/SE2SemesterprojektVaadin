package org.hbrs.se2.project.collhbrs.dtos;


import java.time.LocalDateTime;

public interface AnzeigeDTO {
    int getId();
    String getJobTitle();
    String getStandort();
    String getJobType();
    String getJobDescription();
    CompanyDTO getCompany();
    LocalDateTime getPublicationDate();

    void setId(int id);
    void setJobTitle(String jobTitle);
    void setStandort(String standort);
    void setJobType(String jobType);
    void setJobDescription(String JobDescription);
    void setCompany(CompanyDTO company);
    void setPublicationDate(LocalDateTime publicationDate);
}
