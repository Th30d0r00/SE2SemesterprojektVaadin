package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnectionPrepared;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Dient der Verwaltung von Unternehmen in der Datenbank.
 */

public class CompanyDAO {
    public CompanyDTO getCompanyById(int companyId) throws SQLException, DatabaseLayerException {
        CompanyDTO company = null;
        String sql = "SELECT * FROM collabhbrs.company WHERE id = ?";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sql)) {
            statement.setInt(1, companyId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    company = mapResultSetToCompany(resultSet);
                }
            }
        } catch (DatabaseLayerException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return company;
    }


    public boolean updateCompanyProfileInDB(int companyId, String newCompanyName, LocalDate newFoundingDate, int newEmployees,
                                            String newLocations, String newDescription) throws DatabaseLayerException {
        boolean successfullyUpdatedCompany = false;
        String sql = "UPDATE collabhbrs.company SET name = ?, founding_date = ?, employees = ?, locations = ?, description = ? WHERE id = ?";

        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sql)) {
            statement.setString(1, newCompanyName);
            statement.setDate(2, Date.valueOf(newFoundingDate));
            statement.setInt(3, newEmployees);
            statement.setString(4, newLocations);
            statement.setString(5, newDescription);
            statement.setInt(6, companyId);

            int result = statement.executeUpdate();
            if (result > 0) {
                successfullyUpdatedCompany = true;
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return successfullyUpdatedCompany;
    }


    private CompanyDTO mapResultSetToCompany(ResultSet set) throws SQLException {
        CompanyDTO company = new CompanyDTOImpl();
        company.setId(set.getInt("id"));
        company.setCompanyName(set.getString("company_name"));
        company.setEmployees(set.getInt("employees"));
        company.setFoundingDate(set.getDate("founding_date").toLocalDate());
        company.setLocations(set.getString("locations"));
        company.setDescription(set.getString("description"));
        return company;
    }

    public List<CompanyDTO> getAllCompanies() throws DatabaseLayerException {
        List<CompanyDTO> companies = new ArrayList<>();
        String sql = "SELECT * FROM collabhbrs.company";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sql);
             ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                companies.add(mapResultSetToCompany(set));
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException("Fehler bei Datenbankverbindung!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return companies;
    }

}
