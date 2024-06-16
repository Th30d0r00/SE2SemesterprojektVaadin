package org.hbrs.se2.project.hellocar.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name="anzeige", schema = "carlook")
public class Anzeige {
    private int id;
    private String jobTitle;
    private String companyName;
    private String jobType;
    private String standort;
    private LocalDateTime publicationDate;
    private String jobDescription;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public int getID(){return id;}

    public void setID(int id){ this.id = id;}

    @Basic
    @Column(name = "Titel")
    public String getJobTitle(){return jobTitle;}

    public void setJobTitle(String jobTitle){this.jobTitle = jobTitle;}

    @Basic
    @Column(name = "Unternehmen")
    public String getCompanyName(){return companyName;}

    public void setCompanyName(String companyName){this.companyName = companyName;}

    @Basic
    @Column(name = "Jobart")

    public String getJobType(){return jobType;}

    public void setJobType(String jobType){this.jobType = jobType;}

    @Basic
    @Column(name = "Standort")
    public String getStandort(){return standort;}

    public void setStandort(String standort){this.standort = standort;}

    @Basic
    @Column(name = "Veröffentlichung")
    public LocalDateTime getPublicationDate(){return publicationDate;}

    public void setPublicationDate(LocalDateTime publicationDate){this.publicationDate = publicationDate;}

    @Basic
    @Column(name = "Stellenbeschreibung")
    public String getJobDescription(){return jobDescription;}

    public void setJobDescription(String jobDescription){this.jobDescription = jobDescription;}

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Anzeige anzeige = (Anzeige) o;
        return id == anzeige.id &&
                Objects.equals(jobTitle, anzeige.jobTitle) &&
                Objects.equals(companyName, anzeige.companyName) &&
                Objects.equals(jobType, anzeige.jobType) &&
                Objects.equals(standort, anzeige.standort) &&
                Objects.equals(jobDescription, anzeige.jobDescription);
                /* publicationDate wird nicht verglichen,
                da die duplizierte Stellenanzeige zeitlich
                später ausgestellt werden könnte!*/
    }
}
