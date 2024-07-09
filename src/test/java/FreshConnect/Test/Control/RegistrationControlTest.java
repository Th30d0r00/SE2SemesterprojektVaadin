package FreshConnect.Test.Control;

import org.hbrs.se2.project.hellocar.control.RegistrationControl;
import org.hbrs.se2.project.hellocar.control.factories.DTOFactory;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.RegistrationResult;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationControlTest {
    private static RegistrationControl registrationControl;
    private static UserDTO userDTO;
    private static StudentDTO studentDTO;
    private static UserDTO userThatExists;
    private static UserDAO userDAO = new UserDAO();

    @BeforeAll
    public static void setUp() throws DatabaseLayerException {
        registrationControl = new RegistrationControl();

        // Initialisierung der allgemeinen Testdaten
        studentDTO = DTOFactory.createStudent("max", "tester", LocalDate.of(2020, 4, 8), 1);
        userDTO = DTOFactory.createStudentUser("maxtester" + UUID.randomUUID() + "@gmail.com", "maxtester", studentDTO);

        // Erstellung eines bereits existierenden Nutzers
        StudentDTO existingStudentDTO = DTOFactory.createStudent("peter", "muster", LocalDate.of(2020, 1, 8), 1);
        userThatExists = DTOFactory.createStudentUser("maxtester@gmail.com", "test1234", existingStudentDTO);
        registrationControl.registerUser(userThatExists);
    }

    @AfterAll
    public static void tearDown() throws DatabaseLayerException {
        // Löschung aller Testdaten
        UserDTO userTODelete = userDAO.findUserByEmail(userDTO.getEmail());
        if (userTODelete != null) {
            userDAO.deleteUserProfile(userTODelete.getId());
        }
        userTODelete = userDAO.findUserByEmail(userThatExists.getEmail());
        if (userTODelete != null) {
            userDAO.deleteUserProfile(userTODelete.getId());
        }
    }

    @Test
    public void testRegisterUser_Success() throws DatabaseLayerException {
        RegistrationResult result = registrationControl.registerUser(userDTO);
        assertTrue(result.getSuccess());
        assertEquals("User successfully registered.", result.getMessage());
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() throws DatabaseLayerException {
        RegistrationResult result = registrationControl.registerUser(userThatExists);
        assertFalse(result.getSuccess());
        assertEquals("User with email " + userThatExists.getEmail() + " already exists", result.getMessage());
    }
}
