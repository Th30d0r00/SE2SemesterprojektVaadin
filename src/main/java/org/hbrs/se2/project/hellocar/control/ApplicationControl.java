package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.dao.ApplicationDAO;
import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationControl {

    private ApplicationDAO applicationDAO;

    public ApplicationControl() {
        this.applicationDAO = new ApplicationDAO();
    }

    public void saveApplication(ApplicationDTO application) throws DatabaseLayerException {
        // Speichern der Bewerbung in der Datenbank
        applicationDAO.addApplication(application);
    }
}
