package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.dao.CompanyDAO;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShowCompaniesControl {
    private final CompanyDAO companyDAO;

    public ShowCompaniesControl() {
        this.companyDAO = new CompanyDAO();
    }


    public List<CompanyDTO> readAllCompanies() throws DatabaseLayerException{
            return companyDAO.getAllCompanies();
    }
}
