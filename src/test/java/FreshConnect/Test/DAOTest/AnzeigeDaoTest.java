package FreshConnect.Test.DAOTest;

import org.hbrs.se2.project.collhbrs.control.factories.DTOFactory;
import org.hbrs.se2.project.collhbrs.dao.AnzeigeDAO;
import org.hbrs.se2.project.collhbrs.dao.UserDAO;
import org.hbrs.se2.project.collhbrs.dtos.AnzeigeDTO;
import org.hbrs.se2.project.collhbrs.dtos.CompanyDTO;
import org.hbrs.se2.project.collhbrs.dtos.UserDTO;
import org.hbrs.se2.project.collhbrs.services.db.exceptions.DatabaseLayerException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnzeigeDaoTest {
    // Testklasse für AnzeigeDAO

    private static AnzeigeDAO anzeigeDAO = new AnzeigeDAO();
    private static AnzeigeDTO anzeigeDTO;
    private static UserDTO userDTO;
    private static CompanyDTO companyDTO;
    private static UserDAO userDAO = new UserDAO();


    @BeforeAll
    public static void setup() throws DatabaseLayerException {
        // Erstellung der Studenten, Unternehmen und Benutzer
        companyDTO = DTOFactory.createCompany("FreshConnect",
                LocalDate.of(2018, 11, 13), 9,
                "Bonn", "Eine coole Company");

        userDTO = DTOFactory.createCompanyUser("FreshConnect@gmail.com",
                "987654321", companyDTO);

        userDAO.addUser(userDTO);

        companyDTO.setId(userDAO.findUserByEmail(userDTO.getEmail()).getId());

        userDTO.setId(userDAO.findUserByEmail(userDTO.getEmail()).getId());

        anzeigeDTO = DTOFactory.createAnzeigeDTO("Software Engineer", "Bonn",
                "Full-time", companyDTO, "Develop software", LocalDateTime.now());
    }

    @AfterAll
    public static void teardown() throws DatabaseLayerException {
        // Löschen des Benutzers
        userDAO.deleteUserProfile(userDAO.findUserByEmail(userDTO.getEmail()).getId());
    }

    @Test
    public void testAddAnzeigeTest() throws DatabaseLayerException {
        // Testet das Hinzufügen einer Anzeige
        assertTrue(anzeigeDAO.addAnzeige(anzeigeDTO));
    }

    @Test
    public void getAllAnzeigenTest() throws DatabaseLayerException {
        // Testet das Abrufen aller Anzeigen
        anzeigeDAO.addAnzeige(anzeigeDTO);
        List <AnzeigeDTO> anzeigeDTOList = anzeigeDAO.getAllAnzeigen();
        assertTrue(anzeigeDTOList.size() > 0);
    }

    @Test
    public void getAllMyAnzeigenTest() throws DatabaseLayerException {
        // Testet das Abrufen aller Anzeigen eines Benutzers
        anzeigeDAO.addAnzeige(anzeigeDTO);
        assertTrue(anzeigeDAO.getAllMyJobPostings(companyDTO.getId()).size() > 0);
    }
}
