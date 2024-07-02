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
    AnzeigeDTO getStellenanzeige();
    StudentDTO getStudent();
    CompanyDTO getCompany();

    void setId(int id);
    void setTelefonnummer(String telefonnummer);
    void setBeschaeftigung(String beschaeftigung);
    void setVerfuegbar(LocalDate verfuegbar);
    void setWohnort(String wohnort);
    void setMotivationsschreiben(String motivationsschreiben);
    void setLebenslauf(String lebenslauf);
    void setAppliedAt(LocalDateTime appliedAt);
    void setStatus(String status);
    void setStellenanzeige(AnzeigeDTO stellenanzeige);
    void setStudent(StudentDTO student);
    void setCompany(CompanyDTO company);
}
