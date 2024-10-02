package FreshConnect.Test.DTOTest;


import org.hbrs.se2.project.collhbrs.dtos.AnzeigeDTO;
import org.hbrs.se2.project.collhbrs.dtos.CompanyDTO;
import org.hbrs.se2.project.collhbrs.dtos.StudentDTO;
import org.hbrs.se2.project.collhbrs.dtos.impl.AnzeigeDTOImpl;
import org.hbrs.se2.project.collhbrs.dtos.impl.ApplicationDTOImpl;
import org.hbrs.se2.project.collhbrs.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.collhbrs.dtos.impl.StudentDTOImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@TestMethodOrder(MethodOrderer.Alphanumeric.class)

public class ApplicationDTOImplTest {

    private static ApplicationDTOImpl applicationDTO;
    private static LocalDate verfuegbarDate;
    private static LocalDateTime appliedAtDate;

    @BeforeAll
    public static void setUp() {
        // Initialize the ApplicationDTOImpl object
        verfuegbarDate = LocalDate.now();
        appliedAtDate = LocalDateTime.now();

        applicationDTO = new ApplicationDTOImpl();
        applicationDTO.setId(1);
        applicationDTO.setTelefonnummer("1234567890");
        applicationDTO.setBeschaeftigung("Vollzeit");
        applicationDTO.setVerfuegbar(verfuegbarDate);
        applicationDTO.setWohnort("Berlin");
        applicationDTO.setMotivationsschreiben("Ich bin sehr motiviert.");
        applicationDTO.setLebenslauf("Mein Lebenslauf...");
        applicationDTO.setAppliedAt(appliedAtDate);
        applicationDTO.setStatus("versendet");

        // Create and set related DTOs
        AnzeigeDTO stellenanzeige = new AnzeigeDTOImpl();
        // Set properties for stellenanzeige...
        applicationDTO.setStellenanzeige(stellenanzeige);

        StudentDTO student = new StudentDTOImpl();
        // Set properties for student...
        applicationDTO.setStudent(student);

        CompanyDTO company = new CompanyDTOImpl();
        // Set properties for company...
        applicationDTO.setCompany(company);
    }

    @Test
    public void atestGetId() {
        assertEquals(1, applicationDTO.getId());
    }

    @Test
    public void atestGetTelefonnummer() {
        assertEquals("1234567890", applicationDTO.getTelefonnummer());
    }

    @Test
    public void atestGetBeschaeftigung() {
        assertEquals("Vollzeit", applicationDTO.getBeschaeftigung());
    }

    @Test
    public void atestGetVerfuegbar() {
        assertEquals(verfuegbarDate, applicationDTO.getVerfuegbar());
    }

    @Test
    public void atestGetWohnort() {
        assertEquals("Berlin", applicationDTO.getWohnort());
    }

    @Test
    public void atestGetMotivationsschreiben() {
        assertEquals("Ich bin sehr motiviert.", applicationDTO.getMotivationsschreiben());
    }

    @Test
    public void atestGetLebenslauf() {
        assertEquals("Mein Lebenslauf...", applicationDTO.getLebenslauf());
    }

    @Test
    public void atestGetAppliedAt() {
        assertEquals(appliedAtDate, applicationDTO.getAppliedAt());
    }

    @Test
    public void atestGetStatus() {
        assertEquals("versendet", applicationDTO.getStatus());
    }

    @Test
    public void atestGetStellenanzeige() {
        AnzeigeDTO stellenanzeige = applicationDTO.getStellenanzeige();
        assertNotNull(stellenanzeige);
    }

    @Test
    public void atestGetStudent() {
        StudentDTO student = applicationDTO.getStudent();
        assertNotNull(student);
    }

    @Test
    public void atestGetCompany() {
        CompanyDTO company = applicationDTO.getCompany();
        assertNotNull(company);
    }

    @Test
    public void testSetId() {
        applicationDTO.setId(2);
        assertEquals(2, applicationDTO.getId());
    }

    @Test
    public void testSetTelefonnummer() {
        applicationDTO.setTelefonnummer("0987654321");
        assertEquals("0987654321", applicationDTO.getTelefonnummer());
    }

    @Test
    public void testSetBeschaeftigung() {
        applicationDTO.setBeschaeftigung("Teilzeit");
        assertEquals("Teilzeit", applicationDTO.getBeschaeftigung());
    }

    @Test
    public void testSetVerfuegbar() {
        LocalDate newDate = LocalDate.now();
        applicationDTO.setVerfuegbar(newDate);
        assertEquals(newDate, applicationDTO.getVerfuegbar());
    }

    @Test
    public void testSetWohnort() {
        applicationDTO.setWohnort("Hamburg");
        assertEquals("Hamburg", applicationDTO.getWohnort());
    }

    @Test
    public void testSetMotivationsschreiben() {
        applicationDTO.setMotivationsschreiben("Ich bin sehr motiviert und qualifiziert.");
        assertEquals("Ich bin sehr motiviert und qualifiziert.", applicationDTO.getMotivationsschreiben());
    }

    @Test
    public void testSetLebenslauf() {
        applicationDTO.setLebenslauf("Mein Lebenslauf ist sehr gut.");
        assertEquals("Mein Lebenslauf ist sehr gut.", applicationDTO.getLebenslauf());
    }

    @Test
    public void testSetAppliedAt() {
        LocalDateTime newDate = LocalDateTime.now();
        applicationDTO.setAppliedAt(newDate);
        assertEquals(newDate, applicationDTO.getAppliedAt());
    }

    @Test
    public void testSetStatus() {
        applicationDTO.setStatus("eingegangen");
        assertEquals("eingegangen", applicationDTO.getStatus());
    }

    @Test
    public void testSetStellenanzeige() {
        AnzeigeDTO newAnzeige = new AnzeigeDTOImpl();
        applicationDTO.setStellenanzeige(newAnzeige);
        assertEquals(newAnzeige, applicationDTO.getStellenanzeige());
    }

    @Test
    public void testSetStudent() {
        StudentDTO newStudent = new StudentDTOImpl();
        applicationDTO.setStudent(newStudent);
        assertEquals(newStudent, applicationDTO.getStudent());
    }

    @Test
    public void testSetCompany() {
        CompanyDTO newCompany = new CompanyDTOImpl();
        applicationDTO.setCompany(newCompany);
        assertEquals(newCompany, applicationDTO.getCompany());
    }
}
