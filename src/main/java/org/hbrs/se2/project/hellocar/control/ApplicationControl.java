package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.dao.ApplicationDAO;
import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationControl {

    private ApplicationDAO applicationDAO;

    public ApplicationControl() {
        this.applicationDAO = new ApplicationDAO();
    }

    public boolean saveApplication(ApplicationDTO application) throws DatabaseLayerException {
        // Speichern der Bewerbung in der Datenbank
        return applicationDAO.addApplication(application);
    }

    public ApplicationDTO findApplicationById(int applicationId) throws DatabaseLayerException {
        return applicationDAO.getApplicationById(applicationId);
    }

    public boolean acceptApplication(int applicationId) throws DatabaseLayerException {
        return applicationDAO.acceptApplication(applicationId);
    }

    public boolean refuseApplication(int applicationId) throws DatabaseLayerException {
        return applicationDAO.refuseApplication(applicationId);
    }

    public List<ApplicationDTO> readMyApplications(int id) {
        return applicationDAO.getMyApplications(id);
    }
}
