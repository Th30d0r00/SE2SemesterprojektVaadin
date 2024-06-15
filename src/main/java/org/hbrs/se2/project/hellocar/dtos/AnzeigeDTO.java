package org.hbrs.se2.project.hellocar.dtos;


import java.time.LocalDateTime;

public interface AnzeigeDTO {
    int getID();
    String getJobTitle();
    String getCompanyName();
    String getJobType();
    String getStandort();
    LocalDateTime getPublicationDate();
    String getJobDescription();

     void setID(int id);

     void setCompanyName(String companyName);

     void setJobTitle(String jobTitle);

     void setJobType(String jobType);

     void setStandort(String standort);

     void setPublicationDate(LocalDateTime publicationDate);

     void setJobDescribtion(String JobDescribtion);
}
