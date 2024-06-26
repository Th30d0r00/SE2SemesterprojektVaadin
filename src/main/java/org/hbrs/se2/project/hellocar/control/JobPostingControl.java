package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.dao.AnzeigeDAO;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

public class JobPostingControl {

    private final AnzeigeDAO anzeigeDAO;

    public JobPostingControl() {
        this.anzeigeDAO = new AnzeigeDAO();
    }

    public boolean saveJobPosting(AnzeigeDTO anzeigeDTO) throws DatabaseLayerException {
        return anzeigeDAO.addAnzeige(anzeigeDTO);
    }
}