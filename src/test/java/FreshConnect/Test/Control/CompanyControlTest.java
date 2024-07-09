
package FreshConnect.Test.Control;


import org.hbrs.se2.project.hellocar.control.CompanyControl;
import org.hbrs.se2.project.hellocar.control.factories.DTOFactory;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyControlTest {

    private static CompanyControl companyControl;
    private static UserDAO userDAO;
    private static CompanyDTO companyDTO;
    private static UserDTO userDTO;
    private static int companyId;

    @BeforeAll
    public static void setup() throws DatabaseLayerException {
        companyControl = new CompanyControl();
        userDAO = new UserDAO();

        // Initialize the CompanyDTO object
        companyDTO = DTOFactory.createCompany("FreshConnect",
                LocalDate.of(2018, 11, 13), 9,
                "Bonn", "Eine coole Company");

        // Create a UserDTO object
        userDTO = DTOFactory.createCompanyUser("FreshConnect@freshe.de", "123456", companyDTO);

        // Save the user to the database
        userDAO.addUser(userDTO);

        // Set the company id
        companyId = userDAO.findUserByEmail("FreshConnect@freshe.de").getId();
        companyDTO.setId(companyId);
    }

    @AfterAll
    public static void tearDown() throws DatabaseLayerException {
        // Delete the user from the database
        userDAO.deleteUserProfile(companyDTO.getId());
    }

    @Test
    void findCompany() throws DatabaseLayerException, SQLException {
        CompanyDTO retrievedCompany = companyControl.findCompany(companyId);
        assertNotNull(retrievedCompany);
        assertEquals("FreshConnect", retrievedCompany.getCompanyName(), "Company name should match");
    }

    @Test
    void readAllCompanies() throws DatabaseLayerException {
        List<CompanyDTO> companies = companyControl.readAllCompanies();
        assertNotNull(companies);
        assertFalse(companies.isEmpty(), "Companies list should not be empty");
    }

    @Test
    void updateCompanyProfile() throws DatabaseLayerException, SQLException {
        boolean updateResult = companyControl.updateCompanyProfile(companyId, "Updated Company",
                LocalDate.of(2021, 1, 1), 100, "Updated City", "Updated description");
        assertTrue(updateResult, "Company profile should be updated successfully");

        CompanyDTO updatedCompany = companyControl.findCompany(companyId);
        assertEquals("Updated Company", updatedCompany.getCompanyName(), "Updated company name should match");
        assertEquals(100, updatedCompany.getEmployees(), "Updated employees count should match");
    }
}

