package FreshConnect.Test;

import org.hbrs.se2.project.hellocar.entities.Anzeige;
import org.hbrs.se2.project.hellocar.entities.Application;
import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.entities.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.helger.commons.mock.CommonsAssert.assertEquals;
import static com.helger.commons.mock.CommonsAssert.assertNotEquals;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class ApplicationTest {
    //Test for Application entity
    private static Application application;
    private static LocalDateTime past_now;



    @BeforeAll
    public static void setUp() {
        // Create an Application object

        past_now = LocalDateTime.now();
        application = new Application();
        application.setId(1);
        application.setTelefonnummer("1234567890");
        application.setBeschaeftigung("Vollzeit");
        application.setVerfuegbar(past_now);
        application.setWohnort("Berlin");
        application.setMotivationsschreiben("Ich bin sehr motiviert.");
        application.setLebenslauf("Mein Lebenslauf...");
        application.setAppliedAt(past_now);
        application.setStatus("versendet");

        // Create related entities and set them
        Anzeige stellenanzeige = new Anzeige();

        // Set properties for stellenanzeige...
        application.setStellenanzeige(stellenanzeige);

        Student student = new Student();
        // Set properties for student...
        application.setStudent(student);

        Company company = new Company();
        // Set properties for company...
        application.setCompany(company);
    }

    @Test
    public void atestGetId() {
        assertEquals(1, application.getId());
    }

    @Test
    public void atestGetTelefonnummer() {
        assertEquals("1234567890", application.getTelefonnummer());
    }

    @Test
    public void atestGetBeschaeftigung() {
        assertEquals("Vollzeit", application.getBeschaeftigung());
    }

    @Test
    public void atestGetVerfuegbar() {
        assertEquals(past_now, application.getVerfuegbar());
    }

    @Test
    public void atestGetWohnort() {
        assertEquals("Berlin", application.getWohnort());
    }

    @Test
    public void atestGetMotivationsschreiben() {
        assertEquals("Ich bin sehr motiviert.", application.getMotivationsschreiben());
    }

    @Test
    public void atestGetLebenslauf() {
        assertEquals("Mein Lebenslauf...", application.getLebenslauf());
    }

    @Test
    public void atestGetAppliedAt() {
        assertEquals(past_now, application.getAppliedAt());
    }

    @Test
    public void atestGetStatus() {
        assertEquals("versendet", application.getStatus());
    }

    @Test
    public void atestGetStellenanzeige() {
        // Get the related entity and check its properties
        Anzeige stellenanzeige = application.getStellenanzeige();
        assertNotEquals(null, stellenanzeige);

    }

    @Test
    public void atestGetStudent() {
        // Get the related entity and check its properties
        Student student = application.getStudent();
        assertNotEquals(null, student);
    }

    @Test
    public void atestGetCompany() {
        // Get the related entity and check its properties
        Company company = application.getCompany();
        assertNotEquals(null, company);
    }

    @Test
    public void testSetId() {
        application.setId(2);
        assertEquals(2, application.getId());
    }

    @Test
    public void testSetTelefonnummer() {
        application.setTelefonnummer("0987654321");
        assertEquals("0987654321", application.getTelefonnummer());
    }

    @Test
    public void testSetBeschaeftigung() {
        application.setBeschaeftigung("Teilzeit");
        assertEquals("Teilzeit", application.getBeschaeftigung());
    }

    @Test
    public void testSetVerfuegbar() {
        LocalDateTime future_now = LocalDateTime.now();
        application.setVerfuegbar(future_now);
        assertEquals(future_now, application.getVerfuegbar());
    }

    @Test
    public void testSetWohnort() {
        application.setWohnort("Hamburg");
        assertEquals("Hamburg", application.getWohnort());
    }

    @Test
    public void testSetMotivationsschreiben() {
        application.setMotivationsschreiben("Ich bin sehr motiviert und qualifiziert.");
        assertEquals("Ich bin sehr motiviert und qualifiziert.", application.getMotivationsschreiben());
    }

    @Test
    public void testSetLebenslauf() {
        application.setLebenslauf("Mein Lebenslauf ist sehr gut.");
        assertEquals("Mein Lebenslauf ist sehr gut.", application.getLebenslauf());
    }

    @Test
    public void testSetAppliedAt() {
        LocalDateTime future_now = LocalDateTime.now();
        application.setAppliedAt(future_now);
        assertEquals(future_now, application.getAppliedAt());
    }

    @Test
    public void testSetStatus() {
        application.setStatus("eingegangen");
        assertEquals("eingegangen", application.getStatus());
    }

    @Test
    public void testSetStellenanzeige() {
        Anzeige stellenanzeige = new Anzeige();
        application.setStellenanzeige(stellenanzeige);
        assertEquals(stellenanzeige, application.getStellenanzeige());
    }

    @Test
    public void testSetStudent() {
        Student student = new Student();
        application.setStudent(student);
        assertEquals(student, application.getStudent());
    }

    @Test
    public void testSetCompany() {
        Company company = new Company();
        application.setCompany(company);
        assertEquals(company, application.getCompany());
    }

}