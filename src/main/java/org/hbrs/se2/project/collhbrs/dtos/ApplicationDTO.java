package org.hbrs.se2.project.collhbrs.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    UserDTO getUser();

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
    void setUser(UserDTO user);
}
