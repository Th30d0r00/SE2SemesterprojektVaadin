package FreshConnect.Test.Entities;

import org.hbrs.se2.project.hellocar.entities.Company;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(MethodOrderer.Alphanumeric.class)

public class CompanyTest {

    private static Company company;
    private static LocalDate foundingDate;

    @BeforeAll
    public static void setUp() {
        // Initialize the Company object
        foundingDate = LocalDate.of(2000, 1, 1);

        company = new Company();
        company.setId(1);
        company.setCompanyName("Tech Solutions");
        company.setFoundingDate(foundingDate);
        company.setEmployees(100);
    }

    @Test
    public void atestGetId() {
        assertEquals(1, company.getId());
    }

    @Test
    public void atestGetCompanyName() {
        assertEquals("Tech Solutions", company.getCompanyName());
    }

    @Test
    public void atestGetFoundingDate() {
        assertEquals(foundingDate, company.getFoundingDate());
    }

    @Test
    public void testGetEmployees() {
        assertEquals(100, company.getEmployees());
    }

    @Test
    public void testSetId() {
        company.setId(2);
        assertEquals(2, company.getId());
    }

    @Test
    public void testSetCompanyName() {
        company.setCompanyName("Innovative Tech");
        assertEquals("Innovative Tech", company.getCompanyName());
    }

    @Test
    public void testSetFoundingDate() {
        LocalDate newDate = LocalDate.of(2010, 5, 15);
        company.setFoundingDate(newDate);
        assertEquals(newDate, company.getFoundingDate());
    }

    @Test
    public void testSetEmployees() {
        company.setEmployees(200);
        assertEquals(200, company.getEmployees());
    }
}
