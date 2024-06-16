package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnection;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class ApplicationDAO {

    /**
     * Fügt eine neue Application in die Datenbank ein (Tabelle muss noch erstellt werden).
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

            String query = "INSERT INTO collahbrs.anzeige (motivationsschreiben, status, stellenanzeige, student, veröffentlichung) " +
                    "VALUES ('" + applicationDTO.getMotivationsschreiben() + "', '" +
                    applicationDTO.getStatus() + "', '" +
                    applicationDTO.getStellenanzeige() + "', '" +
                    applicationDTO.getStudent() + "', '" +
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
}
