package org.hbrs.se2.project.collhbrs.dao;

import org.hbrs.se2.project.collhbrs.dtos.*;
import org.hbrs.se2.project.collhbrs.dtos.impl.AnzeigeDTOImpl;
import org.hbrs.se2.project.collhbrs.dtos.impl.ApplicationDTOImpl;
import org.hbrs.se2.project.collhbrs.services.db.JDBCConnectionPrepared;
import org.hbrs.se2.project.collhbrs.services.db.exceptions.DatabaseLayerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dient der Verwaltung von Bewerbungen in der Datenbank.
 */

public class ApplicationDAO {

    // Methode zur Abfrage einer Bewerbung anhand der ID
    public ApplicationDTO getApplicationById(int applicationId) throws DatabaseLayerException {
        String query = "SELECT * FROM collabhbrs.application WHERE id = ?";
        ApplicationDTO applicationDTO = null;
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(query)) {
            statement.setInt(1, applicationId);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    applicationDTO = mapResultSetToApplication(set);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException("Fehler bei Datenbankverbindung!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return applicationDTO;
    }

    // Methode zum Hinzufügen einer neuen Bewerbung in die Datenbank
    public Boolean addApplication(ApplicationDTO applicationDTO) throws DatabaseLayerException {
        boolean successfullyAddedApplication = false;
        String query = applicationDTO.getStellenanzeige() == null
                ? "INSERT INTO collabhbrs.application " +
                "(telefonnummer, beschaeftigung, verfuegbar, wohnort, motivationsschreiben, lebenslauf, applied, status, student_id, company_id, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                : "INSERT INTO collabhbrs.application " +
                "(telefonnummer, beschaeftigung, verfuegbar, wohnort, motivationsschreiben, lebenslauf, applied, status, stellenanzeige_id, student_id, company_id, user_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, applicationDTO.getTelefonnummer());
            statement.setString(2, applicationDTO.getBeschaeftigung());
            statement.setDate(3, Date.valueOf(applicationDTO.getVerfuegbar()));
            statement.setString(4, applicationDTO.getWohnort());
            statement.setString(5, applicationDTO.getMotivationsschreiben());
            statement.setString(6, applicationDTO.getLebenslauf());
            statement.setTimestamp(7, Timestamp.valueOf(applicationDTO.getAppliedAt()));
            statement.setString(8, applicationDTO.getStatus());
            int index = 9;
            if (applicationDTO.getStellenanzeige() != null) {
                statement.setInt(index++, applicationDTO.getStellenanzeige().getId());
            }
            statement.setInt(index++, applicationDTO.getStudent().getId());
            statement.setInt(index++, applicationDTO.getCompany().getId());
            statement.setInt(index, applicationDTO.getUser().getId());

            int result = statement.executeUpdate();
            if (result > 0) {
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        applicationDTO.setId(keys.getInt(1));
                        successfullyAddedApplication = true;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException("Fehler in der Datenbankverbindung!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return successfullyAddedApplication;
    }

    // Methode zur Abbildung eines ResultSets auf ein ApplicationDTO-Objekt
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

        // Verweise im DTO auf Company, Student und Anzeige setzen
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

        // Falls der Verweis auf eine Stellenanzeige null ist, handelt es sich um eine Initiativbewerbung
        if (stellenanzeigeId == 0) {
            AnzeigeDTO anzeigeDTO = new AnzeigeDTOImpl();
            anzeigeDTO.setJobTitle("Initiativbewerbung");
            application.setStellenanzeige(anzeigeDTO);
        } else { // In diesem Fall ist der Verweis nicht null (Bewerbung auf Stellenanzeige)
            AnzeigeDTO anzeige = anzeigeDAO.findAnzeigeById(stellenanzeigeId);
            application.setStellenanzeige(anzeige);
        }
        // Prüfen auf Null-Werte?
        return application;
    }

    // Methode zur Abfrage aller empfangenen Bewerbungen eines Unternehmens anhand der Unternehmens-ID
    public List<ApplicationDTO> getReceivedApplications(int companyId) throws DatabaseLayerException {
        String query = "SELECT * FROM collabhbrs.application WHERE company_id = ?";
        List<ApplicationDTO> applications = new ArrayList<>();
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(query)) {
            statement.setInt(1, companyId);
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    applications.add(mapResultSetToApplication(set));
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException("Fehler bei Datenbankverbindung!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return applications;
    }

    // Methode zum Akzeptieren einer Bewerbung anhand der Bewerbungs-ID
    public boolean acceptApplication(int applicationId) throws DatabaseLayerException {
        String query = "UPDATE collabhbrs.application SET status = 'angenommen' WHERE id = ?";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(query)) {
            statement.setInt(1, applicationId);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
    }

    // Methode zum Ablehnen einer Bewerbung anhand der Bewerbungs-ID
    public boolean refuseApplication(int applicationId) throws DatabaseLayerException {
        String query = "UPDATE collabhbrs.application SET status = 'abgelehnt' WHERE id = ?";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(query)) {
            statement.setInt(1, applicationId);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
    }

    // Methode zur Abfrage aller Bewerbungen eines Studenten anhand der Studenten-ID
    public List<ApplicationDTO> getMyApplications(int id) throws DatabaseLayerException {
        String query = "SELECT * FROM collabhbrs.application WHERE student_id = ?";
        List<ApplicationDTO> applications = new ArrayList<>();
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    applications.add(mapResultSetToApplication(set));
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return applications;
    }

    // Methode zum Löschen einer Bewerbung anhand der Bewerbungs-ID
    public boolean deleteApplication(int applicationId) throws DatabaseLayerException {
        String query = "DELETE FROM collabhbrs.application WHERE id = ?";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(query)) {
            statement.setInt(1, applicationId);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
    }
}
