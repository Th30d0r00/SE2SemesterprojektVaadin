package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.dao.AnzeigeDAO;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

/**
* Implementierung der Control-Klasse AnzeigeControl
* Hier sollen alle Methoden implementiert werden, die für die Steuerung der Anzeigen zuständig sind
* Dazu gehören u.a. Methoden zum Anzeigen von Anzeigen, zum Erstellen von Anzeigen und zum Löschen von Anzeigen
*/

public class AnzeigeControl {

    private AnzeigeDAO anzeigeDAO;

    public AnzeigeControl() {
        this.anzeigeDAO = new AnzeigeDAO();
    }

    public boolean saveAnzeige(AnzeigeDTO anzeige) throws DatabaseLayerException {
        // Speichern der Anzeige in der Datenbank
        return anzeigeDAO.addAnzeige(anzeige);
    }

    public AnzeigeDTO findAnzeige(int anzeigeId) throws DatabaseLayerException {
        // Anzeigen einer Anzeige anhand der ID
        return anzeigeDAO.findAnzeigeById(anzeigeId);
    }

}
