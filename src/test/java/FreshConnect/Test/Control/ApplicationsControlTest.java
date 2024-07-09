/*

package FreshConnect.Test.Control;

import org.hbrs.se2.project.hellocar.control.ApplicationControl;
import org.hbrs.se2.project.hellocar.control.factories.DTOFactory;
import org.hbrs.se2.project.hellocar.dao.ApplicationDAO;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.*;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationsControlTest {

    private static ApplicationControl applicationControl;
    private static UserDAO userDAO;
    private static ApplicationDAO applicationDAO;
    private static ApplicationDTO applicationDTO;
    private static int applicationId;
    private static StudentDTO studentDTO;
    private static CompanyDTO companyDTO;
    private static AnzeigeDTO anzeigeDTO;
    private static UserDTO userDTO;

    @BeforeAll
    public static void setup() throws DatabaseLayerException {
        applicationControl = new ApplicationControl();
        applicationDAO = new ApplicationDAO();
        userDAO = new UserDAO();

        // Erstellung der Studenten, Unternehmen und Benutzer
        studentDTO = DTOFactory.createStudent("Jan", "Kaminski", LocalDate.of(2000, 1, 1), 1);
        companyDTO = DTOFactory.createCompany("FreshConnect", LocalDate.of(2018, 11, 13), 9, "Bonn", "Eine coole Company");
        anzeigeDTO = DTOFactory.createAnzeigeDTO("Software Engineer", "Bonn", "Full-time", companyDTO, "Develop software", LocalDateTime.now());
        userDTO = DTOFactory.createStudentUser("jan.kaminski@example.com", "123456789", studentDTO);

        // Save the user to the database
        userDAO.addUser(userDTO);

        // Save the application to the database
        applicationDTO = DTOFactory.createApplicationDTO("0123456789", "Teilzeit", LocalDate.of(2024, 7, 1), "Bonn", "Motivationsschreiben", "Lebenslauf", LocalDateTime.now(), "Pending", anzeigeDTO, studentDTO, companyDTO, userDTO);
        applicationControl.saveApplication(applicationDTO);

        // Set the Application id
        applicationId = applicationDAO.getApplicationById(applicationDTO.getId()).getId();
        applicationDTO.setId(applicationId);
    }

    @AfterAll
    public static void tearDown() throws DatabaseLayerException {
        // Delete the application from the database
        applicationControl.deleteApplication(applicationDTO.getId());
        // Delete the user from the database
        userDAO.deleteUserProfile(userDTO.getId());
    }

    @Test
    public void testSaveApplication() throws DatabaseLayerException {
        ApplicationDTO newApplicationDTO = DTOFactory.createApplicationDTO("0987654321", "Vollzeit", LocalDate.of(2024, 8, 1), "Berlin", "Neues Motivationsschreiben", "Neuer Lebenslauf", LocalDateTime.now(), "Pending", anzeigeDTO, studentDTO, companyDTO, userDTO);
        boolean result = applicationControl.saveApplication(newApplicationDTO);
        assertTrue(result, "Application should be saved successfully");

        // Clean up
        applicationControl.deleteApplication(newApplicationDTO.getId());
    }

    @Test
    public void testFindApplicationById() throws DatabaseLayerException {
        ApplicationDTO retrievedApplication = applicationControl.findApplicationById(applicationId);
        assertNotNull(retrievedApplication);
        assertEquals("Motivationsschreiben", retrievedApplication.getMotivationsschreiben(), "Application motivation letter should match");
        assertEquals("Lebenslauf", retrievedApplication.getLebenslauf(), "Application CV should match");
    }

    @Test
    public void testAcceptApplication() throws DatabaseLayerException {
        boolean result = applicationControl.acceptApplication(applicationId);
        assertTrue(result, "Application should be accepted successfully");

        ApplicationDTO acceptedApplication = applicationControl.findApplicationById(applicationId);
        assertEquals("Accepted", acceptedApplication.getStatus(), "Application should be marked as accepted");
    }

    @Test
    public void testRefuseApplication() throws DatabaseLayerException {
        boolean result = applicationControl.refuseApplication(applicationId);
        assertTrue(result, "Application should be refused successfully");

        ApplicationDTO refusedApplication = applicationControl.findApplicationById(applicationId);
        assertEquals("Refused", refusedApplication.getStatus(), "Application should be marked as refused");
    }

    @Test
    public void testReadMyApplications() throws DatabaseLayerException {
        List<ApplicationDTO> myApplications = applicationControl.readMyApplications(studentDTO.getId());
        assertNotNull(myApplications);
        assertFalse(myApplications.isEmpty(), "My applications list should not be empty");
    }

    @Test
    public void testReadApplications() throws DatabaseLayerException {
        List<ApplicationDTO> companyApplications = applicationControl.readApplications(companyDTO.getId());
        assertNotNull(companyApplications);
        assertFalse(companyApplications.isEmpty(), "Company applications list should not be empty");
    }

    @Test
    public void testDeleteApplication() throws DatabaseLayerException {
        ApplicationDTO applicationToDelete = DTOFactory.createApplicationDTO("1122334455", "Teilzeit", LocalDate.of(2024, 9, 1), "Hamburg", "Zu löschendes Motivationsschreiben", "Zu löschender Lebenslauf", LocalDateTime.now(), "Pending", anzeigeDTO, studentDTO, companyDTO, userDTO);
        applicationControl.saveApplication(applicationToDelete);

        boolean result = applicationControl.deleteApplication(applicationToDelete.getId());
        assertTrue(result, "Application should be deleted successfully");

        ApplicationDTO deletedApplication = applicationControl.findApplicationById(applicationToDelete.getId());
        assertNull(deletedApplication, "Deleted application should not be found");
    }
}
*/
