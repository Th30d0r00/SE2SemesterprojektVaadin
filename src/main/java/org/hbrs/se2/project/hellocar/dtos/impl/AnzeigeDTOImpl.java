package org.hbrs.se2.project.hellocar.dtos.impl;


import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;

import java.time.LocalDateTime;

public class AnzeigeDTOImpl implements AnzeigeDTO {
    private int id;
    private String jobTitle;
    private String companyName;
    private String jobType;
    private String standort;
    private LocalDateTime publicationDate;
    private String jobDescription;

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void setID(int id) {
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
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
    public String getStandort() {
        return standort;
    }

    @Override
    public void setStandort(String standort) {
        this.standort = standort;
    }

    @Override
    public LocalDateTime getPublicationDate() {
        return publicationDate;
    }

    @Override
    public void setPublicationDate(LocalDateTime publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public void setJobDescribtion(String JobDescribtion) {
        this.jobDescription = jobDescription;
    }
    @Override
    public String getJobDescription() {
        return jobDescription;
    }

}

