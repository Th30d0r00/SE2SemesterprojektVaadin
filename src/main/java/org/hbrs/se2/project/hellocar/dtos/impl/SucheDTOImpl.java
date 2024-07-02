package org.hbrs.se2.project.hellocar.dtos.impl;

import org.hbrs.se2.project.hellocar.dtos.SucheDTO;
import org.hbrs.se2.project.hellocar.entities.Company;
import java.time.LocalDateTime;
import java.util.List;

public class SucheDTOImpl implements SucheDTO {
    private String jobTitle;
    private List<String> standort;
    private List<String> jobType;
    private String jobDescription;
    private Company company;
    private LocalDateTime publicationDate;


    @Override
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {this.jobTitle = jobTitle;}

    @Override
    public String getCompany() {
        return company.getCompanyName();
    }

    public void setCompany(Company company) {this.company = company;}

    @Override
    public List<String> getJobType() {
        return jobType;
    }

    public void setJobType(List<String> jobType) {this.jobType = jobType;}

    @Override
    public List<String> getStandort() {
        return standort;
    }

    public void setStandort(List<String> standort) {this.standort = standort;}

    @Override
    public String getPublicationDate() {
        return String.valueOf(publicationDate);
    }

    public void setPublicationDate(LocalDateTime publicationDate) {this.publicationDate = publicationDate;}

    @Override
    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {this.jobDescription = jobDescription;}
}
