package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.AnzeigeDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnectionPrepared;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Dient der Verwaltung von Anzeigen in der Datenbank.
 */

public class AnzeigeDAO {

    public boolean addAnzeige(AnzeigeDTO anzeigeDTO) throws DatabaseLayerException {
        String query = "INSERT INTO collabhbrs.anzeige (titel, company_id, jobart, standort, veroeffentlichung, stellenbeschreibung) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(query,Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, anzeigeDTO.getJobTitle());
            statement.setInt(2, anzeigeDTO.getCompany().getId());
            statement.setString(3, anzeigeDTO.getJobType());
            statement.setString(4, anzeigeDTO.getStandort());
            statement.setTimestamp(5, Timestamp.valueOf(anzeigeDTO.getPublicationDate()));
            statement.setString(6, anzeigeDTO.getJobDescription());

            int result = statement.executeUpdate();
            if (result > 0) {
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        anzeigeDTO.setId(keys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return false;
    }

    public AnzeigeDTO findAnzeigeById(int id) throws DatabaseLayerException {
        String query = "SELECT * FROM collabhbrs.anzeige WHERE id = ?";
        AnzeigeDTO anzeige = null;
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    anzeige = mapResultSetToAnzeige(set);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return anzeige;
    }

    public boolean updateJobPostingInDB(int id, String jobTitle, String location, String jobType, String description) throws DatabaseLayerException {
        boolean successResult = false;
        String query = "UPDATE collabhbrs.anzeige SET titel = ?, standort = ?, jobart = ?, stellenbeschreibung = ? WHERE id = ?";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(query)) {
            statement.setString(1, jobTitle);
            statement.setString(2, location);
            statement.setString(3, jobType);
            statement.setString(4, description);
            statement.setInt(5, id);
            int result = statement.executeUpdate();
            successResult = result > 0;
        } catch (SQLException | DatabaseLayerException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return  successResult;
    }

    public List<AnzeigeDTO> getAllAnzeigen() throws DatabaseLayerException {
        String query = "SELECT * FROM collabhbrs.anzeige";
        List<AnzeigeDTO> anzeigen = new ArrayList<>();
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(query);
             ResultSet set = statement.executeQuery()) {
            while (set.next()) {
                anzeigen.add(mapResultSetToAnzeige(set));
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return anzeigen;
    }


    public List<AnzeigeDTO> getAllMyJobPostings(int id) throws DatabaseLayerException {
        String query = "SELECT * FROM collabhbrs.anzeige WHERE company_id = ?";
        List<AnzeigeDTO> anzeigen = new ArrayList<>();
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    anzeigen.add(mapResultSetToAnzeige(set));
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return anzeigen;
    }


    private AnzeigeDTO mapResultSetToAnzeige(ResultSet set) throws SQLException, DatabaseLayerException {
        AnzeigeDTO anzeige = new AnzeigeDTOImpl();
        CompanyDAO companyDAO = new CompanyDAO();
        anzeige.setId(set.getInt("id"));
        anzeige.setJobTitle(set.getString("Titel"));
        anzeige.setJobType(set.getString("Jobart"));
        anzeige.setStandort(set.getString("Standort"));
        anzeige.setPublicationDate(set.getTimestamp("Veroeffentlichung").toLocalDateTime());
        anzeige.setJobDescription(set.getString("Stellenbeschreibung"));

        int companyId = set.getInt("company_id");
        CompanyDTO company = companyDAO.getCompanyById(companyId);
        anzeige.setCompany(company);

        return anzeige;
    }

    public boolean deleteAnzeige(int id) throws DatabaseLayerException {
        String query = "DELETE FROM collabhbrs.anzeige WHERE id = ?";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(query)) {
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
    }

}
