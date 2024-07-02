package FreshConnect.Test.DTOTest;

import org.hbrs.se2.project.hellocar.dtos.SucheDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.SucheDTOImpl;
import org.hbrs.se2.project.hellocar.entities.Company;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)

public class SucheDTOImplTest {

    private static SucheDTOImpl sucheDTO;
    private static Company company;
    private static LocalDateTime publicationDate;

    @BeforeAll
    public static void setUp() {
        // Initialize the SucheDTO object
        sucheDTO = new SucheDTOImpl();
        sucheDTO.setJobTitle("Software Engineer");
        sucheDTO.setJobDescription("Develop and maintain software applications.");

        List<String> standorte = Arrays.asList("Berlin", "Hamburg");
        sucheDTO.setStandort(standorte);

        List<String> jobTypes = Arrays.asList("Full-time", "Part-time");
        sucheDTO.setJobType(jobTypes);

        company = new Company();
        company.setCompanyName("TechCorp");
        sucheDTO.setCompany(company);

        publicationDate = LocalDateTime.of(2023, 7, 1, 12, 0);
        sucheDTO.setPublicationDate(publicationDate);
    }

    @Test
    public void atestGetJobTitle() {
        assertEquals("Software Engineer", sucheDTO.getJobTitle());
    }

    @Test
    public void testSetJobTitle() {
        sucheDTO.setJobTitle("Data Scientist");
        assertEquals("Data Scientist", sucheDTO.getJobTitle());
    }

    @Test
    public void atestGetCompany() {
        assertEquals("TechCorp", sucheDTO.getCompany());
    }

    @Test
    public void testSetCompany() {
        Company newCompany = new Company();
        newCompany.setCompanyName("DataCorp");
        sucheDTO.setCompany(newCompany);
        assertEquals("DataCorp", sucheDTO.getCompany());
    }

    @Test
    public void testGetJobType() {
        List<String> jobTypes = sucheDTO.getJobType();
        assertNotNull(jobTypes);
        assertEquals(2, jobTypes.size());
        assertEquals("Full-time", jobTypes.get(0));
        assertEquals("Part-time", jobTypes.get(1));
    }

    @Test
    public void testSetJobType() {
        List<String> newJobTypes = Arrays.asList("Contract", "Internship");
        sucheDTO.setJobType(newJobTypes);
        List<String> jobTypes = sucheDTO.getJobType();
        assertNotNull(jobTypes);
        assertEquals(2, jobTypes.size());
        assertEquals("Contract", jobTypes.get(0));
        assertEquals("Internship", jobTypes.get(1));
    }

    @Test
    public void atestGetStandort() {
        List<String> standorte = sucheDTO.getStandort();
        assertNotNull(standorte);
        assertEquals(2, standorte.size());
        assertEquals("Berlin", standorte.get(0));
        assertEquals("Hamburg", standorte.get(1));
    }

    @Test
    public void testSetStandort() {
        List<String> newStandorte = Arrays.asList("Munich", "Stuttgart");
        sucheDTO.setStandort(newStandorte);
        List<String> standorte = sucheDTO.getStandort();
        assertNotNull(standorte);
        assertEquals(2, standorte.size());
        assertEquals("Munich", standorte.get(0));
        assertEquals("Stuttgart", standorte.get(1));
    }

    @Test
    public void atestGetPublicationDate() {
        assertEquals("2023-07-01T12:00", sucheDTO.getPublicationDate());
    }

    @Test
    public void testSetPublicationDate() {
        LocalDateTime newPublicationDate = LocalDateTime.of(2024, 1, 1, 9, 0);
        sucheDTO.setPublicationDate(newPublicationDate);
        assertEquals("2024-01-01T09:00", sucheDTO.getPublicationDate());
    }

    @Test
    public void atestGetJobDescription() {
        assertEquals("Develop and maintain software applications.", sucheDTO.getJobDescription());
    }

    @Test
    public void testSetJobDescription() {
        sucheDTO.setJobDescription("Analyze data and build models.");
        assertEquals("Analyze data and build models.", sucheDTO.getJobDescription());
    }
}
