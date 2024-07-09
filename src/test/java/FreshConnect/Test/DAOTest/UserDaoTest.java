package FreshConnect.Test.DAOTest;

import org.hbrs.se2.project.hellocar.control.factories.DTOFactory;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserDaoTest {
    private static UserDTO userDTOStudent;
    private static UserDTO userDTOCompany;
    private static CompanyDTO companyDTO;
    private static StudentDTO studentDTO;
    private static UserDAO userDAO = new UserDAO();

    @BeforeAll
    public static void setup() throws DatabaseLayerException {

        // Create a CompanyDTO object
        companyDTO = DTOFactory.createCompany("FreshConnect",
                LocalDate.of(2018, 11, 13), 9,
                "Bonn", "Eine coole Company");

        // Create a UserDTO object for Company
        userDTOCompany = DTOFactory.createCompanyUser("FreshConnect@freshe.de", "123456", companyDTO);

        // Create a StudentDTO object
        studentDTO = DTOFactory.createStudent("Tom", "Student", LocalDate.of(1999, 1, 1), 1);

        // Create a UserDTO object for Student
        userDTOStudent = DTOFactory.createStudentUser("tomstudent@gmail.com", "123456", studentDTO);
    }

    @AfterAll
    public static void tearDown() throws DatabaseLayerException {
        // Save the id of the users
        userDTOCompany.setId(userDAO.findUserByEmail("FreshConnect@freshe.de").getId());
        userDTOStudent.setId(userDAO.findUserByEmail("tomstudent@gmail.com").getId());

        // Delete the users from the database
        userDAO.deleteUserProfile(userDTOCompany.getId());
        userDAO.deleteUserProfile(userDTOStudent.getId());
    }

    @Test
    public void addUserAsStudentTest() throws DatabaseLayerException {
        assertTrue(userDAO.addUser(userDTOStudent), "User was added to the database");
        UserDTO studentThatWasAdded = userDAO.findUserByEmail("tomstudent@gmail.com");
        assertEquals(userDTOStudent.getEmail(), studentThatWasAdded.getEmail());
        assertEquals(userDTOStudent.getRole(), studentThatWasAdded.getRole());
        assertEquals(userDTOStudent.getAccountType(), studentThatWasAdded.getAccountType());
        assertEquals(userDTOStudent.getStudent().getFirstname(), studentThatWasAdded.getStudent().getFirstname());
        assertEquals(userDTOStudent.getStudent().getLastname(), studentThatWasAdded.getStudent().getLastname());
        assertEquals(userDTOStudent.getStudent().getBirthday(), studentThatWasAdded.getStudent().getBirthday());
        assertEquals(userDTOStudent.getStudent().getFachsemester(), studentThatWasAdded.getStudent().getFachsemester());
    }

    @Test
    public void addUserAsCompanyTest() throws DatabaseLayerException {
        assertTrue(userDAO.addUser(userDTOCompany), "User was added to the database");
        UserDTO companyThatWasAdded = userDAO.findUserByEmail("FreshConnect@freshe.de");
        assertEquals(userDTOCompany.getEmail(), companyThatWasAdded.getEmail());
        assertEquals(userDTOCompany.getRole(), companyThatWasAdded.getRole());
        assertEquals(userDTOCompany.getAccountType(), companyThatWasAdded.getAccountType());
        assertEquals(userDTOCompany.getCompany().getCompanyName(), companyThatWasAdded.getCompany().getCompanyName());
        assertEquals(userDTOCompany.getCompany().getFoundingDate(), companyThatWasAdded.getCompany().getFoundingDate());
        assertEquals(userDTOCompany.getCompany().getEmployees(), companyThatWasAdded.getCompany().getEmployees());
        assertEquals(userDTOCompany.getCompany().getLocations(), companyThatWasAdded.getCompany().getLocations());
        assertEquals(userDTOCompany.getCompany().getDescription(), companyThatWasAdded.getCompany().getDescription());
    }
}
