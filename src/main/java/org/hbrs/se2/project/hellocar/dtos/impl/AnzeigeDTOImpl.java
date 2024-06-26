package org.hbrs.se2.project.hellocar.dtos.impl;


import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.entities.Company;

import java.time.LocalDateTime;

public class AnzeigeDTOImpl implements AnzeigeDTO {
    private int id;
    private String jobTitle;
    private String standort;
    private String jobType;
    private UserDTO company;
    private String jobDescription;
    private LocalDateTime publicationDate;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getJobTitle() {
        return jobTitle;
    }

    @Override
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public String getStandort() {
        return standort;
    }

    @Override
    public void setStandort(String standort) {
        this.standort = standort;
    }

    @Override
    public String getJobType() {
        return jobType;
    }

    @Override
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    @Override
    public UserDTO getCompany() {
        return company;
    }

    @Override
    public void setCompany(UserDTO company) {
        this.company = company;
    }

    @Override
    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    @Override
    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    @Override
    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }
}
