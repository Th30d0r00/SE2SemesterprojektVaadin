package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnection;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CompanyDAO {
    public Company getCompanyById(int companyId) throws SQLException {
        Company company = null;
        String sql = "SELECT * FROM collabhbrs.company WHERE id = " + companyId;
        try (Statement statement = JDBCConnection.getInstance().getStatement();) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                company = mapResultSetToCompany(resultSet);
            }
        } catch (DatabaseLayerException e) {
            throw new RuntimeException(e);
        }
        return company;
    }

    private Company mapResultSetToCompany(ResultSet set) throws SQLException {
        Company company = new Company();
        company.setId(set.getInt("id"));
        company.setCompanyName(set.getString("company_name"));
        company.setEmployees(set.getInt("employees"));
        company.setFoundingDate(set.getDate("founding_date").toLocalDate());
        return company;
    }
}
