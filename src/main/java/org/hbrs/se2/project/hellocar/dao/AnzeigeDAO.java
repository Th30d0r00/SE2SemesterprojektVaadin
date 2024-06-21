package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.entities.Anzeige;
import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnection;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnzeigeDAO {

    public boolean addAnzeige(AnzeigeDTO anzeigeDTO) throws DatabaseLayerException {
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();

            String query = "INSERT INTO collabhbrs.anzeige (titel, company_id, jobart, standort, veröffentlichung, stellenbeschreibung) " +
                    "VALUES ('" + anzeigeDTO.getJobTitle() + "', '" +
                    anzeigeDTO.getCompany().getId() + "', '" +
                    anzeigeDTO.getJobType() + "', '" +
                    anzeigeDTO.getStandort() + "', '" +
                    Timestamp.valueOf(anzeigeDTO.getPublicationDate()) + "', '" +
                    anzeigeDTO.getJobDescription() + "')";

            int result = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            if (result > 0) {
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    anzeigeDTO.setId(keys.getInt(1));
                    return true;
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException("Fehler in der Datenbankverbindung!");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return false;
    }

    public Anzeige findAnzeigeById(int id) throws DatabaseLayerException {
        Anzeige anzeige = null;
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            ResultSet set = statement.executeQuery(
                    "SELECT * FROM collabhbrs.anzeige WHERE id = " + id
            );

            if (set.next()) {
                anzeige = mapResultSetToAnzeige(set);
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException("Fehler bei Datenbankverbindung!");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return anzeige;
    }

    public List<Anzeige> getAllAnzeigen() throws DatabaseLayerException {
        List<Anzeige> anzeigen = new ArrayList<>();
        try {
            Statement statement = JDBCConnection.getInstance().getStatement();
            ResultSet set = statement.executeQuery("SELECT * FROM collabhbrs.anzeige");

            while (set.next()) {
                anzeigen.add(mapResultSetToAnzeige(set));
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException("Fehler bei Datenbankverbindung!");
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
        return anzeigen;
    }

    private Anzeige mapResultSetToAnzeige(ResultSet set) throws SQLException {
        Anzeige anzeige = new Anzeige();
        CompanyDao companyDao = new CompanyDao();
        anzeige.setId(set.getInt("id"));
        anzeige.setJobTitle(set.getString("Titel"));
        anzeige.setJobType(set.getString("Jobart"));
        anzeige.setStandort(set.getString("Standort"));
        anzeige.setPublicationDate(set.getTimestamp("Veröffentlichung").toLocalDateTime());
        anzeige.setJobDescription(set.getString("Stellenbeschreibung"));

        int companyId = set.getInt("company_id");
        Company company = companyDao.getCompanyById(companyId);
        anzeige.setCompany(company);

        return anzeige;
    }

}
