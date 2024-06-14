/*
package FreshConnect.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


import org.hbrs.se2.project.hellocar.control.RegistrationControl;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.junit.Before;
import org.junit.Test;
import org.hbrs.se2.demo.registration.RegistrationResult;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.junit.jupiter.api.BeforeAll;
import org.hbrs.se2.project.hellocar.util.Security;
import org.hbrs.se2.project.hellocar.util.AccountType;

import java.time.LocalDate;

public class RegistrationControlTest {
    private RegistrationControl registrationControl;

    @BeforeAll
    public static void setUp() {
        //RegistrationControl registrationControl = new RegistrationControl();

    }

    @Test
    public void testRegisterUser_Success() throws DatabaseLayerException {
        //create new User with random values
        RegistrationControl registrationControl = new RegistrationControl();

        UserDTO userToAdd = new UserDTOImpl();
        userToAdd.setUserId("PeterMustermann");
        userToAdd.setEmail("adadad@gdddmail.com");
        userToAdd.setPassword("kakaadadddad1234");
        userToAdd.setSalt(Security.getSalt());
        userToAdd.setHashValue(Security.getHash(userToAdd.getPassword(),userToAdd.getSalt()));
        userToAdd.setAccountType(AccountType.valueOf("STUDENT"));

        StudentDTO studentToAdd = new StudentDTOImpl();
        studentToAdd.setFirstname("peter");
        studentToAdd.setLastname("muster");
        LocalDate date = LocalDate.of(2020, 1, 8);
        studentToAdd.setBirthday(date);
        userToAdd.setStudent(studentToAdd);

        //register user
        RegistrationResult result = registrationControl.registerUser(userToAdd);

        //if user is already registered an Assertion Error will be thrown

        try{
            assertTrue(result.getSuccess());
            assertEquals("User successfully registered.", result.getMessage());

            return;
        } catch (AssertionError e) { //if user is already registered
            e.printStackTrace();
        }
        //if user is already registered we will test if the user is already registered
        assertFalse(result.getSuccess());
        assertEquals(("User with email "+ userToAdd.getEmail() + " already exists"), result.getMessage());
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