package FreshConnect.Test.Control;

import org.hbrs.se2.project.collhbrs.control.JobPostingControl;
import org.hbrs.se2.project.collhbrs.control.factories.DTOFactory;
import org.hbrs.se2.project.collhbrs.dao.UserDAO;
import org.hbrs.se2.project.collhbrs.dtos.AnzeigeDTO;
import org.hbrs.se2.project.collhbrs.dtos.CompanyDTO;
import org.hbrs.se2.project.collhbrs.dtos.UserDTO;
import org.hbrs.se2.project.collhbrs.services.db.exceptions.DatabaseLayerException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class JopPostingControlTest {

    private static AnzeigeDTO anzeigeDTO;
    private static UserDTO userDTO;
    private static CompanyDTO companyDTO;
    private static UserDAO userDAO = new UserDAO();
    private static JobPostingControl jobPostingControl = new JobPostingControl();

    @BeforeAll
    public static void setup() throws DatabaseLayerException {

        // Initialize the AnzeigeDTO object
        companyDTO = DTOFactory.createCompany("FreshConnect",
                LocalDate.of(2018,11,13), 9,
                "Bonn","Eine coole Company");

        //Create a UserDTO object
        userDTO = DTOFactory.createCompanyUser("FreshConnect@freshe.de", "123456", companyDTO);

        //Save the user to the database
        userDAO.addUser(userDTO);

        //Set the company id
        companyDTO.setId(userDAO.findUserByEmail("FreshConnect@freshe.de").getId());

        // Initialize the AnzeigeDTO object
        anzeigeDTO = DTOFactory.createAnzeigeDTO("Werkstudent IT Software Testing", "Bonn",
                "Teilzeit", companyDTO, "Eine coole Stellenanzeige", LocalDateTime.now());

    }

    @AfterAll
    public static void tearDown() throws DatabaseLayerException {
        //Delete the user from the database, AnzeigeDTO is also deleted because of the foreign key constraint
        assertTrue(userDAO.deleteUserProfile(companyDTO.getId()));
    }

    @Test
    // Test if the saveJobPosting method works correctly
    void saveJobPostingTest() throws DatabaseLayerException {
        boolean saveResult = jobPostingControl.saveJobPosting(anzeigeDTO);
        assertTrue(saveResult, "Job posting should be saved successfully");
    }

    @Test
    // Test if the readAllJobPostings method works correctly
    void readAllJobPostingsTest() throws DatabaseLayerException {
        JobPostingControl jobPostingControl = new JobPostingControl();
        List<AnzeigeDTO> anzeigenList = jobPostingControl.readAllJobPostings();
        assertFalse(anzeigenList.isEmpty(), "There should be at least one job posting in the database");
    }

    @Test
    void updateJobPostingTest() throws DatabaseLayerException {
        boolean updateResult = jobPostingControl.updateJobPosting(anzeigeDTO.getId(), "Werkstudent IT Software Testing", "Bonn",
                "Vollzeit", "Eine coole Stellenanzeige");
        assertTrue(updateResult, "Job posting should be updated successfully");
    }

    @Test
    void readAllMyJobPostingsTest() throws DatabaseLayerException {
        List<AnzeigeDTO> anzeigenList = jobPostingControl.readAllMyJobPostings(companyDTO.getId());
        assertFalse(anzeigenList.isEmpty(), "There should be at least one job posting in the database");
    }

}
