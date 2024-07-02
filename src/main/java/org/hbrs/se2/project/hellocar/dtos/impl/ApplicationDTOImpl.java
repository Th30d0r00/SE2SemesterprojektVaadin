package org.hbrs.se2.project.hellocar.dtos.impl;

import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import org.hbrs.se2.project.hellocar.entities.Anzeige;
import org.hbrs.se2.project.hellocar.entities.ApplicationFile;
import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.entities.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ApplicationDTOImpl implements ApplicationDTO {
    private int id;
    private String telefonnummer;
    private String beschaeftigung;
    private LocalDate verfuegbar;
    private String wohnort;
    private String motivationsschreiben;
    private String lebenslauf;
    private LocalDateTime appliedAt;
    private String status; // z.B. versendet, eingegangen, beantwortet -> Benachrichtigung an Student bei Statusänderung
    private Anzeige stellenanzeige; // Verlinkt mit Stellenanzeige, null bei Initiativbewebrung
    private Student student; // Verlinkt mit Student
    private Company company;

    //Attribute von Student:
    private String firstName;
    private String lastName;
    private int fachSemester;
    private LocalDate birthday;

    //Attribute von Stellenanzeige:
    private String jobTitel;
    private String standort;

    //Attribute von User:
    private String email;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTelefonnummer() {
        return telefonnummer;
    }

    @Override
    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    @Override
    public String getBeschaeftigung() {
        return beschaeftigung;
    }

    @Override
    public void setBeschaeftigung(String beschaeftigung) {
        this.beschaeftigung = beschaeftigung;
    }

    @Override
    public LocalDate getVerfuegbar() {
        return verfuegbar;
    }

    public void setVerfuegbar(LocalDate verfuegbar) {
        this.verfuegbar = verfuegbar;
    }

    @Override
    public String getWohnort() {
        return wohnort;
    }

    @Override
    public void setWohnort(String wohnort) {
        this.wohnort = wohnort;
    }

    @Override
    public String getMotivationsschreiben() {
        return motivationsschreiben;
    }

    @Override
    public void setMotivationsschreiben(String motivationsschreiben) {
        this.motivationsschreiben = motivationsschreiben;
    }

    @Override
    public String getLebenslauf() {
        return lebenslauf;
    }

    @Override
    public void setLebenslauf(String lebenslauf) {
        this.lebenslauf = lebenslauf;
    }

    @Override
    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    @Override
    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Anzeige getStellenanzeige() {
        return stellenanzeige;
    }

    @Override
    public void setStellenanzeige(Anzeige stellenanzeige) {
        this.stellenanzeige = stellenanzeige;
    }

    @Override
    public Student getStudent() {
        return student;
    }

    @Override
    public Company getCompany() {
        return this.company;
    }

    @Override
    public String getFirstname() {
        return firstName;
    }

    @Override
    public void setFirstname(String firstname) {
        this.firstName = firstname;
    }

    @Override
    public String getLastname() {
        return lastName;
    }

    @Override
    public void setLastname(String lastname) {
        this.lastName = lastname;
    }

    @Override
    public int getFachsemester() {
        return fachSemester;
    }

    @Override
    public void setFachsemester(int fachSemester) {
        this.fachSemester = fachSemester;
    }

    @Override
    public LocalDate getBirthday() {
        return birthday;
    }

    @Override
    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String getJobTitle() {
        return jobTitel;
    }

    @Override
    public void setJobTitel(String jobTitel) {
        this.jobTitel = jobTitel;
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
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public void setCompany(Company company) {
        this.company = company;
    }
}
