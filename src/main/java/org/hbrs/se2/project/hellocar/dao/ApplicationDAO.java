package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.dtos.*;
import org.hbrs.se2.project.hellocar.dtos.impl.AnzeigeDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.ApplicationDTOImpl;
import org.hbrs.se2.project.hellocar.entities.Anzeige;
import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.entities.Student;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnection;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.springframework.data.relational.core.sql.In;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    /**
     * Fügt eine neue Application in die Datenbank ein.
     * Die Verlinkung zu Student (eindeutig) und zu einer Stellenanzeige (darf null sein bei Initiativb.)
     * müsste noch richtig implementiert werden.
     * @param applicationId
     * @return
     * @throws DatabaseLayerException
     */
    public ApplicationDTO getApplicationById(int applicationId) throws DatabaseLayerException {
        ApplicationDTO applicationDTO = null;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            ResultSet set = statement.executeQuery(
                    "SELECT * FROM collabhbrs.application WHERE id = " + applicationId
            );

            if (set.next()) {
                applicationDTO = mapResultSetToApplication(set);
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException("Fehler bei Datenbankverbindung!");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return applicationDTO;
    }
    public Boolean addApplication(ApplicationDTO applicationDTO) throws DatabaseLayerException{
        boolean successfullyAddesApplication = false;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            String query = "";

            if(applicationDTO.getStellenanzeige() == null) {
                query = "INSERT INTO collabhbrs.application " +
                        "(telefonnummer, beschaeftigung, verfuegbar, wohnort, motivationsschreiben, lebenslauf, applied, status, student_id, company_id, user_id) " +
                        "VALUES ('" +
                        applicationDTO.getTelefonnummer() + "', '" +
                        applicationDTO.getBeschaeftigung() + "', '" +
                        Date.valueOf(applicationDTO.getVerfuegbar()) + "', '" +
                        applicationDTO.getWohnort() + "', '" +
                        applicationDTO.getMotivationsschreiben() + "', '" +
                        applicationDTO.getLebenslauf() + "', '" +
                        applicationDTO.getAppliedAt() + "', '" +
                        applicationDTO.getStatus() + "', " +
                        applicationDTO.getStudent().getId() + "," +
                        applicationDTO.getCompany().getId() + "," +
                        applicationDTO.getUser().getId() +
                        ")";
            } else {
                query = "INSERT INTO collabhbrs.application " +
                        "(telefonnummer, beschaeftigung, verfuegbar, wohnort, motivationsschreiben, lebenslauf, applied, status, stellenanzeige_id, student_id, company_id, user_id) " +
                        "VALUES ('" +
                        applicationDTO.getTelefonnummer() + "', '" +
                        applicationDTO.getBeschaeftigung() + "', '" +
                        Date.valueOf(applicationDTO.getVerfuegbar()) + "', '" +
                        applicationDTO.getWohnort() + "', '" +
                        applicationDTO.getMotivationsschreiben() + "', '" +
                        applicationDTO.getLebenslauf() + "', '" +
                        applicationDTO.getAppliedAt() + "', '" +
                        applicationDTO.getStatus() + "', " +
                        applicationDTO.getStellenanzeige().getId() + ", " +
                        applicationDTO.getStudent().getId() + "," +
                        applicationDTO.getCompany().getId() + "," +
                        applicationDTO.getUser().getId() +
                        ")";
            }

            int result = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (result > 0) {
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    applicationDTO.setId(keys.getInt(1));
                    successfullyAddesApplication = true;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException("Fehler in der Datenbankverbindung!");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return successfullyAddesApplication;
    }

    private ApplicationDTO mapResultSetToApplication(ResultSet set) throws SQLException, DatabaseLayerException {
        UserDAO userDAO = new UserDAO();
        AnzeigeDAO anzeigeDAO = new AnzeigeDAO();
        CompanyDAO companyDAO = new CompanyDAO();
        StudentDAO studentDAO = new StudentDAO();
        ApplicationDTO application = new ApplicationDTOImpl();

        application.setId(set.getInt("id"));
        application.setTelefonnummer(set.getString("telefonnummer"));
        application.setBeschaeftigung(set.getString("beschaeftigung"));
        application.setVerfuegbar(set.getDate("verfuegbar").toLocalDate());
        application.setWohnort(set.getString("wohnort"));
        application.setMotivationsschreiben(set.getString("motivationsschreiben"));
        application.setLebenslauf(set.getString("lebenslauf"));
        application.setAppliedAt(set.getDate("applied").toLocalDate().atStartOfDay());
        application.setStatus(set.getString("status"));

        //Verweise im DTO auf Company, Student und Anzeige setzen:
        int companyId = set.getInt("company_id");
        CompanyDTO company = companyDAO.getCompanyById(companyId);
        application.setCompany(company);

        int studentId = set.getInt("student_id");
        StudentDTO student = studentDAO.getStudentById(studentId);
        application.setStudent(student);

        int userId = set.getInt("user_id");
        UserDTO user = userDAO.findUserById(userId);
        application.setUser(user);

        Integer stellenanzeigeId = set.getInt("stellenanzeige_id");

        //Falls der Verweis auf eine Stellenanzeige null ist, handelt es sich um eine Initiativbewerbung
        if(stellenanzeigeId == 0) {
            AnzeigeDTO anzeigeDTO = new AnzeigeDTOImpl();
            anzeigeDTO.setJobTitle("Initiativbewerbung");
            application.setStellenanzeige(anzeigeDTO);
        } else { //In diesem Fall ist der Verweis nicht null (Bewerbung auf Stellenanzeige)
            AnzeigeDTO anzeige = anzeigeDAO.findAnzeigeById(stellenanzeigeId);
            application.setStellenanzeige(anzeige);
        }
        // Prüfen auf Null-Werte?
        return application;
    }
    public List<ApplicationDTO> getReceivedApplications(int companyId) throws DatabaseLayerException {
        List<ApplicationDTO> applications = new ArrayList<>();
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            ResultSet set = statement.executeQuery("SELECT * "
                    + "FROM collabhbrs.application "
                    + "WHERE collabhbrs.application.company_id = \'" + companyId + "\'" ); //CompanyID gibts nicht in der DB -> brauchen Verlinkung

            while (set.next()) {
                applications.add(mapResultSetToApplication(set));
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException("Fehler bei Datenbankverbindung!");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return applications;
    }
}
