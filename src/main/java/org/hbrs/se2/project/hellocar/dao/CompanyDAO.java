package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnection;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CompanyDAO {
    public CompanyDTO getCompanyById(int companyId) throws SQLException {
        CompanyDTO company = null;
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

    private CompanyDTO mapResultSetToCompany(ResultSet set) throws SQLException {
        CompanyDTO company = new CompanyDTOImpl();
        company.setId(set.getInt("id"));
        company.setCompanyName(set.getString("company_name"));
        company.setEmployees(set.getInt("employees"));
        company.setFoundingDate(set.getDate("founding_date").toLocalDate());
        return company;
    }
}
