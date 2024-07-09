package FreshConnect.Test.Control;

import org.hbrs.se2.project.hellocar.control.StudentControl;
import org.hbrs.se2.project.hellocar.control.factories.DTOFactory;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentControlTest {
    private static StudentDTO studentDTO;
    private static UserDTO userDTO;
    private static DTOFactory dtoFactory = new DTOFactory();
    private static UserDAO userDAO = new UserDAO();
    private static StudentControl studentControl = new StudentControl();

    @BeforeAll
    public static void setUp() throws DatabaseLayerException {
        // Initialize the StudentDTO object
        studentDTO = dtoFactory.createStudent("Max", "Mustermann1234", LocalDate.of(1999, 1, 1), 1);

        // Initialize the UserDTO object
        userDTO = dtoFactory.createStudentUser("mustermann1234@gmail.com", "123456", studentDTO);

        // Add the user to the database
        userDAO.addUser(userDTO);

        // Save the id of the user
        userDTO.setId(userDAO.findUserByEmail("mustermann1234@gmail.com").getId());
    }

    @AfterAll
    public static void tearDown() throws DatabaseLayerException {
        // Delete the user from the database
        userDAO.deleteUserProfile(userDTO.getId());
    }

    @Test
    public void findStudentTest() throws DatabaseLayerException, SQLException {
        // Find the student in the database
        StudentDTO studentThatWasFound = studentControl.findStudent(userDTO.getId());
        assertEquals(studentDTO.getFirstname(), studentThatWasFound.getFirstname());
        assertEquals(studentDTO.getLastname(), studentThatWasFound.getLastname());
        assertEquals(studentDTO.getBirthday(), studentThatWasFound.getBirthday());
        assertEquals(studentDTO.getFachsemester(), studentThatWasFound.getFachsemester());
    }

    @Test
    public void updateStudentProfileTest() throws DatabaseLayerException {
        // Update the student profile
        assertTrue(studentControl.updateStudentProfile(userDTO.getId(), "Max", "Mustermann",
                LocalDate.of(1999, 1, 1), 2), "Student profile was updated");
    }
}
