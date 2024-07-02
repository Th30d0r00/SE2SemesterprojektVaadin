package org.hbrs.se2.project.hellocar.dtos;

import org.hbrs.se2.project.hellocar.entities.Anzeige;
import org.hbrs.se2.project.hellocar.entities.ApplicationFile;
import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.entities.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationDTO {
    int getId();
    String getTelefonnummer();
    String getBeschaeftigung();
    LocalDate getVerfuegbar();
    String getWohnort();
    String getMotivationsschreiben();
    String getLebenslauf();
    LocalDateTime getAppliedAt();
    String getStatus();
    Anzeige getStellenanzeige();
    Student getStudent();
    Company getCompany();

    void setId(int id);
    void setTelefonnummer(String telefonnummer);
    void setBeschaeftigung(String beschaeftigung);
    void setVerfuegbar(LocalDate verfuegbar);
    void setWohnort(String wohnort);
    void setMotivationsschreiben(String motivationsschreiben);
    void setLebenslauf(String lebenslauf);
    void setAppliedAt(LocalDateTime appliedAt);
    void setStatus(String status);
    void setStellenanzeige(Anzeige stellenanzeige);
    void setStudent(Student student);
    void setCompany(Company company);

    //Attribute von Student:
    String getFirstname();
    void setFirstname(String firstname);
    String getLastname();
    void setLastname(String lastname);
    int getFachsemester();
    void setFachsemester(int fachSemester);
    LocalDate getBirthday();
    void setBirthday(LocalDate birthday);

    //Attribute von Stellenanzeige:
    String getJobTitle();
    void setJobTitel(String jobTitel);
    String getStandort();
    void setStandort(String standort);

    //Attribute von User:
    String getEmail();
    void setEmail(String email);

}
