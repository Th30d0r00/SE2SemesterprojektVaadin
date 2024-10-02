package org.hbrs.se2.project.collhbrs.control;

import org.hbrs.se2.project.collhbrs.dao.AnzeigeDAO;
import org.hbrs.se2.project.collhbrs.dtos.AnzeigeDTO;
import org.hbrs.se2.project.collhbrs.services.db.exceptions.DatabaseLayerException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementierung der Control-Klasse JobPostingControl
 * Hier sollen alle Methoden implementiert werden, die für die Steuerung der Anzeigen zuständig sind
 * Dazu gehören u.a. Methoden zum Anzeigen von Anzeigen, zum Erstellen von Anzeigen und zum Löschen von Anzeigen
 */

@Service
public class JobPostingControl {

    private final AnzeigeDAO anzeigeDAO;

    public JobPostingControl() {
        this.anzeigeDAO = new AnzeigeDAO();
    }

    public boolean saveJobPosting(AnzeigeDTO anzeigeDTO) throws DatabaseLayerException {
        return anzeigeDAO.addAnzeige(anzeigeDTO);
    }

    public List<AnzeigeDTO> readAllJobPostings() throws DatabaseLayerException {
        return anzeigeDAO.getAllAnzeigen();
    }

    public boolean updateJobPosting(int id, String jobTitle, String location, String jobType, String description) throws DatabaseLayerException {
        return anzeigeDAO.updateJobPostingInDB(id, jobTitle, location, jobType, description);
    }

    public boolean deleteJobPosting(int id) throws DatabaseLayerException {
        return anzeigeDAO.deleteAnzeige(id);
    }

    public AnzeigeDTO findJobPosting(int anzeigeId) throws DatabaseLayerException {
        // Anzeigen einer Anzeige anhand der ID
        return anzeigeDAO.findAnzeigeById(anzeigeId);
    }

    public List<AnzeigeDTO> readAllMyJobPostings(int companyId) throws DatabaseLayerException {
        return anzeigeDAO.getAllMyJobPostings(companyId);
    }
}