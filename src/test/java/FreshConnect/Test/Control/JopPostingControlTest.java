package FreshConnect.Test.Control;

import org.hbrs.se2.project.hellocar.control.JobPostingControl;
import org.hbrs.se2.project.hellocar.control.factories.DTOFactory;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class JopPostingControlTest {

    private static AnzeigeDTO anzeigeDTO;
    private static UserDTO userDTO;
    private static CompanyDTO companyDTO;
    private static UserDAO userDAO = new UserDAO();
    private static int companyId;
    private int testJobPostingId;
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
        userDAO.deleteUserProfile(companyDTO.getId());
    }

    @Test
    // Test if the saveJobPosting method works correctly
    void saveJobPosting() throws DatabaseLayerException {
        boolean saveResult = jobPostingControl.saveJobPosting(anzeigeDTO);
        assertTrue(saveResult, "Job posting should be saved successfully");
        testJobPostingId = anzeigeDTO.getId();
    }

    @Test
    // Test if the readAllJobPostings method works correctly
    void readAllJobPostings() throws DatabaseLayerException {
        JobPostingControl jobPostingControl = new JobPostingControl();
        jobPostingControl.readAllJobPostings();
    }

}
