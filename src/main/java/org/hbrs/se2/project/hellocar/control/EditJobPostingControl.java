package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.dao.AnzeigeDAO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.springframework.stereotype.Service;

@Service
public class EditJobPostingControl {
    private final AnzeigeDAO anzeigeDAO;

    public EditJobPostingControl() {
        anzeigeDAO = new AnzeigeDAO();
    }

    public boolean updateJobPosting(int id, String jobTitle, String location, String jobType, String description) {
        return anzeigeDAO.updateJobPostingInDB(id, jobTitle, location, jobType, description);
    }

    public boolean deleteJobPosting(int id) throws DatabaseLayerException {
        return anzeigeDAO.deleteAnzeige(id);
    }
}
