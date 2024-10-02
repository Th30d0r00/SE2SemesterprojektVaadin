package FreshConnect.Test.Entities;

import org.hbrs.se2.project.collhbrs.entities.Anzeige;
import org.hbrs.se2.project.collhbrs.entities.Company;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class AnzeigeTest {

    private static Anzeige anzeige;
    private static LocalDateTime publicationDate;

    @BeforeAll
    public static void setUp() {
        // Initialize the Anzeige object
        publicationDate = LocalDateTime.now();
        anzeige = new Anzeige();
        anzeige.setId(1);
        anzeige.setJobTitle("Softwareentwickler");
        anzeige.setStandort("Köln");
        anzeige.setJobType("Vollzeit");
        anzeige.setJobDescription("Entwicklung von Softwarelösungen.");
        anzeige.setPublicationDate(publicationDate);

        // Create and set a Company object
        Company company = new Company();
        // Set properties for company...
        anzeige.setCompany(company);
    }

    @Test
    public void atestGetId() {
        assertEquals(1, anzeige.getId());
    }

    @Test
    public void atestGetJobTitle() {
        assertEquals("Softwareentwickler", anzeige.getJobTitle());
    }

    @Test
    public void atestGetStandort() {
        assertEquals("Köln", anzeige.getStandort());
    }

    @Test
    public void atestGetJobType() {
        assertEquals("Vollzeit", anzeige.getJobType());
    }

    @Test
    public void atestGetJobDescription() {
        assertEquals("Entwicklung von Softwarelösungen.", anzeige.getJobDescription());
    }

    @Test
    public void atestGetPublicationDate() {
        assertEquals(publicationDate, anzeige.getPublicationDate());
    }

    @Test
    public void atestGetCompany() {
        Company company = anzeige.getCompany();
        assertNotNull(company);
    }

    @Test
    public void testSetId() {
        anzeige.setId(2);
        assertEquals(2, anzeige.getId());
    }

    @Test
    public void testSetJobTitle() {
        anzeige.setJobTitle("Projektmanager");
        assertEquals("Projektmanager", anzeige.getJobTitle());
    }

    @Test
    public void testSetStandort() {
        anzeige.setStandort("Berlin");
        assertEquals("Berlin", anzeige.getStandort());
    }

    @Test
    public void testSetJobType() {
        anzeige.setJobType("Teilzeit");
        assertEquals("Teilzeit", anzeige.getJobType());
    }

    @Test
    public void testSetJobDescription() {
        anzeige.setJobDescription("Leitung von Projekten.");
        assertEquals("Leitung von Projekten.", anzeige.getJobDescription());
    }

    @Test
    public void testSetPublicationDate() {
        LocalDateTime newDate = LocalDateTime.now();
        anzeige.setPublicationDate(newDate);
        assertEquals(newDate, anzeige.getPublicationDate());
    }

    @Test
    public void testSetCompany() {
        Company newCompany = new Company();
        anzeige.setCompany(newCompany);
        assertEquals(newCompany, anzeige.getCompany());
    }
}

