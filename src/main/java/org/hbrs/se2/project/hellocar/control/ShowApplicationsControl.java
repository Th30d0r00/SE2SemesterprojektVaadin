package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.dao.ApplicationDAO;
import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowApplicationsControl {

    private final ApplicationDAO applicationDAO;
    public ShowApplicationsControl() {
        this.applicationDAO = new ApplicationDAO();
    }
    public List<ApplicationDTO> readApplications(int companyId) throws DatabaseLayerException {
        return applicationDAO.getReceivedApplications(companyId);
    }
}
