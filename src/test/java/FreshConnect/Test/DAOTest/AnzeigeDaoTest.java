package FreshConnect.Test.DAOTest;

import org.hbrs.se2.project.hellocar.dao.AnzeigeDAO;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class AnzeigeDaoTest {
    // Testklasse für AnzeigeDAO

    private AnzeigeDAO anzeigeDAO = new AnzeigeDAO();

    @Test
    public void testgetAllAnzeigen() throws DatabaseLayerException {
        // Testmethode für getAllAnzeigen
        List<AnzeigeDTO> anzeigen = anzeigeDAO.getAllAnzeigen();
        assert(!anzeigen.isEmpty());
        for (AnzeigeDTO anzeige : anzeigen) {
            System.out.println(anzeige.getJobTitle());
        }
    }
}
