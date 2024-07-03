package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.dao.CompanyDAO;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

import java.sql.SQLException;

/**
 * Implementierung der Control-Klasse CompanyControl
 * Hier sollen alle Methoden implementiert werden, die für die Steuerung einer Company zuständig sind
 * Dazu gehören u.a. Methoden zum Anzeigen von Anzeigen, zum Erstellen und zum Löschen einer Company
 */

public class CompanyControl {
    private CompanyDAO companyDAO;

    public CompanyControl() {
        this.companyDAO = new CompanyDAO();
    }

    public CompanyDTO findCompany(int companyId) throws DatabaseLayerException, SQLException {
        // Anzeigen einer Company anhand der ID
        return companyDAO.getCompanyById(companyId);
    }
}
