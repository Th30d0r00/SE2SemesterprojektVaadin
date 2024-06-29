package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.dao.AnzeigeDAO;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowMyJobPostingsControl {
    private final AnzeigeDAO anzeigeDAO;

    public ShowMyJobPostingsControl() {
        this.anzeigeDAO = new AnzeigeDAO();
    }

    public List<AnzeigeDTO> readAllMyJobPostings(int companyId) throws DatabaseLayerException {
        return anzeigeDAO.getAllMyJobPostings(companyId);
    }
}
