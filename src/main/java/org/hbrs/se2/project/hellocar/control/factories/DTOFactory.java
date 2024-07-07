package org.hbrs.se2.project.hellocar.control.factories;

import org.hbrs.se2.project.hellocar.dtos.*;
import org.hbrs.se2.project.hellocar.dtos.impl.*;
import org.hbrs.se2.project.hellocar.util.AccountType;
import org.hbrs.se2.project.hellocar.util.Security;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DTOFactory {
    public static UserDTO createStudentUser(String email, String password, StudentDTO student) {
        UserDTOImpl userDTO = new UserDTOImpl();
        userDTO.setEmail(email);
        userDTO.setRole("USER");
        userDTO.setAccountType(AccountType.STUDENT);
        userDTO.setStudent(student);

        // Salt und Hash generieren
        byte[] salt = Security.getSalt();
        userDTO.setSalt(salt);
        userDTO.setHashValue(Security.getHash(password, salt));

        return userDTO;
    }

    public static UserDTO createCompanyUser(String email, String password, CompanyDTO company) {
        UserDTOImpl userDTO = new UserDTOImpl();
        userDTO.setEmail(email);
        userDTO.setRole("USER");
        userDTO.setAccountType(AccountType.UNTERNEHMEN);
        userDTO.setCompany(company);

        // Salt und Hash generieren
        byte[] salt = Security.getSalt();
        userDTO.setSalt(salt);
        userDTO.setHashValue(Security.getHash(password, salt));

        return userDTO;
    }

    public static StudentDTO createStudent(String firstname, String lastname, LocalDate birthday, int fachsemester) {
        StudentDTOImpl studentDTO = new StudentDTOImpl();
        studentDTO.setFirstname(firstname);
        studentDTO.setLastname(lastname);
        studentDTO.setBirthday(birthday);
        studentDTO.setFachsemester(fachsemester);
        return studentDTO;
    }

    public static CompanyDTO createCompany(String companyName, LocalDate foundingDate, int employees, String locations, String description) {
        CompanyDTOImpl companyDTO = new CompanyDTOImpl();
        companyDTO.setCompanyName(companyName);
        companyDTO.setFoundingDate(foundingDate);
        companyDTO.setEmployees(employees);
        companyDTO.setLocations(locations);
        companyDTO.setDescription(description);
        return companyDTO;
    }

    public static ApplicationDTO createApplicationDTO(int id, String telefonnummer, String beschaeftigung, LocalDate verfuegbar, String wohnort, String motivationsschreiben, String lebenslauf, LocalDateTime appliedAt, String status, AnzeigeDTO stellenanzeige, StudentDTO student, CompanyDTO company, UserDTO user) {
        ApplicationDTO applicationDTO = new ApplicationDTOImpl();
        applicationDTO.setId(id);
        applicationDTO.setTelefonnummer(telefonnummer);
        applicationDTO.setBeschaeftigung(beschaeftigung);
        applicationDTO.setVerfuegbar(verfuegbar);
        applicationDTO.setWohnort(wohnort);
        applicationDTO.setMotivationsschreiben(motivationsschreiben);
        applicationDTO.setLebenslauf(lebenslauf);
        applicationDTO.setAppliedAt(appliedAt);
        applicationDTO.setStatus(status);
        applicationDTO.setStellenanzeige(stellenanzeige);
        applicationDTO.setStudent(student);
        applicationDTO.setCompany(company);
        applicationDTO.setUser(user);
        return applicationDTO;
    }

    public static AnzeigeDTO createAnzeigeDTO(int id, String jobTitle, String standort, String jobType, CompanyDTO company, String jobDescription, LocalDateTime publicationDate) {
        AnzeigeDTO anzeigeDTO = new AnzeigeDTOImpl();
        anzeigeDTO.setId(id);
        anzeigeDTO.setJobTitle(jobTitle);
        anzeigeDTO.setStandort(standort);
        anzeigeDTO.setJobType(jobType);
        anzeigeDTO.setCompany(company);
        anzeigeDTO.setJobDescription(jobDescription);
        anzeigeDTO.setPublicationDate(publicationDate);
        return anzeigeDTO;
    }


}
