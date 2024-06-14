/*
package FreshConnect.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.hbrs.se2.demo.registration.RegistrationControl;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.junit.Before;
import org.junit.Test;
import org.hbrs.se2.demo.registration.RegistrationResult;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.junit.jupiter.api.BeforeAll;

public class RegistrationControlTest {
    private RegistrationControl registrationControl;

    @BeforeAll
    public void setUp() {

        registrationControl = new RegistrationControl();
    }

    @Test
    public void testRegisterUser_Success() throws DatabaseLayerException {

        userDTO = new UserDTOImpl();
        userDTO.setEmail("abc@gmail.com");
        userDTO.setUserId("abc");
        userDTO.setPassword("abc");
        userDTO.setAccountType(AccountType.STUDENT);
        userDTO.setFirstname("abc");
        userDTO.setLastname("abc");
        userDTO.setBirthday(LocalDate.of(2000, 1, 1));


        RegistrationResult result = registrationControl.registerUser(neuerBenutzer);

        assertEquals(true, result);
        assertEquals("User successfully registered.", result.getMessage());
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() throws DatabaseLayerException {
        UserDTO neuerBenutzer = new UserDTOImpl();
        neuerBenutzer.setEmail("test@example.com");



        RegistrationResult result = registrationControl.registerUser(neuerBenutzer);

        assertFalse(result.getSuccess());
        assertEquals("User with email test@example.com already exists", result.getMessage());
    }

    @Test
    public void testRegisterUser_AddUserFails() throws DatabaseLayerException {
        UserDTO neuerBenutzer = new UserDTOImpl();
        neuerBenutzer.setEmail("test@example.com");



        RegistrationResult result = registrationControl.registerUser(neuerBenutzer);

        assertFalse(result.getSuccess());
        assertEquals("Couldn't register user.", result.getMessage());
    }

    @Test
    public void testRegisterUser_DatabaseLayerException() throws DatabaseLayerException {
        UserDTO neuerBenutzer = new UserDTOImpl();
        neuerBenutzer.setEmail("test@example.com");


        RegistrationResult result = registrationControl.registerUser(neuerBenutzer);

        assertFalse(result.getSuccess());
        assertNull(result.getMessage());
    }
}
 */
