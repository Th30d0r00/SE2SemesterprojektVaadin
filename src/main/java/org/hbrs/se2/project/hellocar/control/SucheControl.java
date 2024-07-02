package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.dao.AnzeigeDAO;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.SucheDTO;
import org.hbrs.se2.project.hellocar.entities.Anzeige;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SucheControl {
    private List<AnzeigeDTO> alleAnzeigen;
    private final AnzeigeDAO anzeigeDAO;
    private LocalDateTime lastUpdate;
    private static final long UPDATE_INTERVAL_MINUTES = 5;

    public SucheControl() {
        anzeigeDAO = new AnzeigeDAO();
        // Initiales Abrufen der Anzeigen
        aktualisiereAnzeigenListe();
    }

    private synchronized void aktualisiereAnzeigenListe() {
        try {
            alleAnzeigen = anzeigeDAO.getAllAnzeigen();
        } catch (DatabaseLayerException e) {
            throw new RuntimeException(e);
        }
        lastUpdate = LocalDateTime.now();
        System.out.println("Anzeigenliste aktualisiert: " + alleAnzeigen.size() + " Anzeigen geladen.");
    }

    public List<AnzeigeDTO> sucheAnzeigen(SucheDTO sucheDTO) throws DatabaseLayerException {
        if (lastUpdate == null || lastUpdate.plusMinutes(UPDATE_INTERVAL_MINUTES).isBefore(LocalDateTime.now())) {
            System.out.println("Liste wird aufgrund des Zeitintervalls von 5 Minuten aktualisiert.");
            aktualisiereAnzeigenListe();
        }

        // Filtern der Anzeigen nach den Suchkriterien
        return alleAnzeigen.stream()
                .filter(anzeige ->
                        (sucheDTO.getJobTitle() == null || anzeige.getJobTitle().toLowerCase().contains(sucheDTO.getJobTitle().toLowerCase())) &&
                                (sucheDTO.getJobType() == null || sucheDTO.getJobType().isEmpty() || sucheDTO.getJobType().stream().anyMatch(anzeige.getJobType()::contains)) &&
                                (sucheDTO.getStandort() == null || sucheDTO.getStandort().isEmpty() || sucheDTO.getStandort().stream().anyMatch(anzeige.getStandort()::contains)))
                .collect(Collectors.toList());

    }

}
