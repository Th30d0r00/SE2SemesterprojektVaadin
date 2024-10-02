package FreshConnect.Test.DTOTest;

import org.hbrs.se2.project.collhbrs.dtos.CompanyDTO;
import org.hbrs.se2.project.collhbrs.dtos.impl.CompanyDTOImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(MethodOrderer.Alphanumeric.class)

public class CompanyDTOImplTest {

    private static CompanyDTO companyDTO;
    private static LocalDate foundingDate;

    @BeforeAll
    public static void setUp() {
        // Initialize the CompanyDTO object
        foundingDate = LocalDate.of(2000, 1, 1);

        companyDTO = new CompanyDTOImpl();
        companyDTO.setId(1);
        companyDTO.setCompanyName("Tech Solutions");
        companyDTO.setFoundingDate(foundingDate);
        companyDTO.setEmployees(100);
        companyDTO.setLocations("Berlin, Hamburg");
        companyDTO.setDescription("Innovative tech company");
    }

    @Test
    public void atestGetId() {
        assertEquals(1, companyDTO.getId());
    }

    @Test
    public void atestGetCompanyName() {
        assertEquals("Tech Solutions", companyDTO.getCompanyName());
    }

    @Test
    public void atestGetFoundingDate() {
        assertEquals(foundingDate, companyDTO.getFoundingDate());
    }

    @Test
    public void atestGetEmployees() {
        assertEquals(100, companyDTO.getEmployees());
    }

    @Test
    public void atestGetLocations() {
        assertEquals("Berlin, Hamburg", companyDTO.getLocations());
    }

    @Test
    public void atestGetDescription() {
        assertEquals("Innovative tech company", companyDTO.getDescription());
    }

    @Test
    public void testSetId() {
        companyDTO.setId(2);
        assertEquals(2, companyDTO.getId());
    }

    @Test
    public void testSetCompanyName() {
        companyDTO.setCompanyName("Innovative Tech");
        assertEquals("Innovative Tech", companyDTO.getCompanyName());
    }

    @Test
    public void testSetFoundingDate() {
        LocalDate newDate = LocalDate.of(2010, 5, 15);
        companyDTO.setFoundingDate(newDate);
        assertEquals(newDate, companyDTO.getFoundingDate());
    }

    @Test
    public void testSetEmployees() {
        companyDTO.setEmployees(200);
        assertEquals(200, companyDTO.getEmployees());
    }

    @Test
    public void testSetLocations() {
        companyDTO.setLocations("Munich, Stuttgart");
        assertEquals("Munich, Stuttgart", companyDTO.getLocations());
    }

    @Test
    public void testSetDescription() {
        companyDTO.setDescription("Leading tech company");
        assertEquals("Leading tech company", companyDTO.getDescription());
    }
}

