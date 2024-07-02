package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.ApplicationDTOImpl;
import org.hbrs.se2.project.hellocar.entities.Anzeige;
import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.entities.Student;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnection;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    /**
     * Fügt eine neue Application in die Datenbank ein.
     * Die Verlinkung zu Student (eindeutig) und zu einer Stellenanzeige (darf null sein bei Initiativb.)
     * müsste noch richtig implementiert werden.
     * @param applicationDTO
     * @return
     * @throws DatabaseLayerException
     */
    public Boolean addApplication(ApplicationDTO applicationDTO) throws DatabaseLayerException{
        boolean successfullyAddesApplication = false;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();

            String query = "INSERT INTO collabhbrs.application (motivationsschreiben, status, stellenanzeige, student, veröffentlichung) " +
                    "VALUES ('" + applicationDTO.getMotivationsschreiben() + "', '" +
                    applicationDTO.getStatus() + "', '" +
                    applicationDTO.getStellenanzeige().getId() + "', '" +
                    applicationDTO.getStudent().getId() + "', '" +
                    Timestamp.valueOf(applicationDTO.getAppliedAt()) + "')";

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
        UserDTO company = userDAO.findUserById(companyId);
        application.setCompany((Company) company);

        int studentId = set.getInt("student_id");
        UserDTO student = userDAO.findUserById(studentId);
        application.setStudent((Student) student);

        //Fremde Attribute aus User, Student und Stellenanzeige hinzufügen:
        UserDTO userDTO = userDAO.findUserById(studentId);
        StudentDTO studentDTO = (StudentDTO) userDAO.findUserById(studentId);

        application.setEmail(userDTO.getEmail());
        application.setFirstname(studentDTO.getFirstname());
        application.setLastname(studentDTO.getLastname());
        application.setFachsemester(studentDTO.getFachsemester());
        application.setBirthday(studentDTO.getBirthday());

        //Falls der Verweis auf eine Stellenanzeige null ist, handelt es sich um eine Initiativbewerbung
        if(application.getStellenanzeige() == null) {
            application.setJobTitel("Initiativbewerbung");
            application.setStandort("-");
        } else { //In diesem Fall ist der Verweis nicht null (Bewerbung auf Stellenanzeige)
            int stellenanzeigeId = set.getInt("stellenanzeige_id");
            AnzeigeDTO anzeige = anzeigeDAO.findAnzeigeById(stellenanzeigeId);
            application.setStellenanzeige((Anzeige) anzeige);
            AnzeigeDTO anzeigeDTO = anzeigeDAO.findAnzeigeById(stellenanzeigeId);
            application.setJobTitel(anzeigeDTO.getJobTitle());
            application.setStandort(anzeigeDTO.getStandort());
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
