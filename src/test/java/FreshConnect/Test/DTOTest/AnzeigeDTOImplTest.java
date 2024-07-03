package FreshConnect.Test.DTOTest;

import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.AnzeigeDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@TestMethodOrder(MethodOrderer.Alphanumeric.class)

public class AnzeigeDTOImplTest {

    private static AnzeigeDTOImpl anzeigeDTO;
    private static LocalDateTime publicationDate;

    @BeforeAll
    public static void setUp() {
        // Initialize the AnzeigeDTOImpl object
        publicationDate = LocalDateTime.now();

        anzeigeDTO = new AnzeigeDTOImpl();
        anzeigeDTO.setId(1);
        anzeigeDTO.setJobTitle("Softwareentwickler");
        anzeigeDTO.setStandort("Berlin");
        anzeigeDTO.setJobType("Vollzeit");
        anzeigeDTO.setJobDescription("Entwicklung von Softwarelösungen.");
        anzeigeDTO.setPublicationDate(publicationDate);

        // Create and set a related UserDTO (company)
        CompanyDTO company = new CompanyDTOImpl();
        // Set properties for company...
        anzeigeDTO.setCompany(company);
    }

    @Test
    public void atestGetId() {
        assertEquals(1, anzeigeDTO.getId());
    }

    @Test
    public void atestGetJobTitle() {
        assertEquals("Softwareentwickler", anzeigeDTO.getJobTitle());
    }

    @Test
    public void atestGetStandort() {
        assertEquals("Berlin", anzeigeDTO.getStandort());
    }

    @Test
    public void atestGetJobType() {
        assertEquals("Vollzeit", anzeigeDTO.getJobType());
    }

    @Test
    public void atestGetJobDescription() {
        assertEquals("Entwicklung von Softwarelösungen.", anzeigeDTO.getJobDescription());
    }

    @Test
    public void atestGetPublicationDate() {
        assertEquals(publicationDate, anzeigeDTO.getPublicationDate());
    }

    @Test
    public void atestGetCompany() {
        CompanyDTO company = anzeigeDTO.getCompany();
        assertNotNull(company);
    }

    @Test
    public void testSetId() {
        anzeigeDTO.setId(2);
        assertEquals(2, anzeigeDTO.getId());
    }

    @Test
    public void testSetJobTitle() {
        anzeigeDTO.setJobTitle("Projektmanager");
        assertEquals("Projektmanager", anzeigeDTO.getJobTitle());
    }

    @Test
    public void testSetStandort() {
        anzeigeDTO.setStandort("Hamburg");
        assertEquals("Hamburg", anzeigeDTO.getStandort());
    }

    @Test
    public void testSetJobType() {
        anzeigeDTO.setJobType("Teilzeit");
        assertEquals("Teilzeit", anzeigeDTO.getJobType());
    }

    @Test
    public void testSetJobDescription() {
        anzeigeDTO.setJobDescription("Projektmanagement und Kundenbetreuung.");
        assertEquals("Projektmanagement und Kundenbetreuung.", anzeigeDTO.getJobDescription());
    }

    @Test
    public void testSetPublicationDate() {
        LocalDateTime newDate = LocalDateTime.now();
        anzeigeDTO.setPublicationDate(newDate);
        assertEquals(newDate, anzeigeDTO.getPublicationDate());
    }

    @Test
    public void testSetCompany() {
        CompanyDTO newCompany = new CompanyDTOImpl();
        anzeigeDTO.setCompany(newCompany);
        assertEquals(newCompany, anzeigeDTO.getCompany());
    }
}
