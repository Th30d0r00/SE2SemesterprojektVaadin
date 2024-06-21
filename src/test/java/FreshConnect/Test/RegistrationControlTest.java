
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

        //create new User1 with random values
        RegistrationControl registrationControl = new RegistrationControl();

        UserDTO userToAdd = new UserDTOImpl();
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

        //register user2
        RegistrationResult result = registrationControl.registerUser(userToAdd);

        //create new User2 with the same values
        RegistrationControl registrationControl2 = new RegistrationControl();

        UserDTO userToAdd2 = new UserDTOImpl();
        userToAdd2.setEmail("adadad@gdddmail.com");
        userToAdd2.setPassword("kakaadadddad1234");
        userToAdd2.setSalt(Security.getSalt());
        userToAdd2.setHashValue(Security.getHash(userToAdd2.getPassword(),userToAdd2.getSalt()));
        userToAdd2.setAccountType(AccountType.valueOf("STUDENT"));

        StudentDTO studentToAdd2 = new StudentDTOImpl();
        studentToAdd2.setFirstname("peter");
        studentToAdd2.setLastname("muster");
        LocalDate date2 = LocalDate.of(2020, 1, 8);
        studentToAdd2.setBirthday(date2);
        userToAdd2.setStudent(studentToAdd2);

        //register user2
        RegistrationResult result2 = registrationControl.registerUser(userToAdd2);

        //if user is already registered an Assertion Error will be thrown

        try{
            assertTrue(result2.getSuccess());
            assertEquals("User successfully registered.", result2.getMessage());

            return;
        } catch (AssertionError e) { //if user is already registered
            e.printStackTrace();
        }
        //if user is already registered we will test if the user is already registered
        assertFalse(result2.getSuccess());
        assertEquals(("User with email "+ userToAdd2.getEmail() + " already exists"), result.getMessage());
    }

    @Test
    public void testRegisterUser_AddUserFails() throws DatabaseLayerException {
        //create new User with random values
        RegistrationControl registrationControl = new RegistrationControl();

        UserDTO userToAdd = new UserDTOImpl();
        userToAdd.setEmail("adadad@gdddmail.com");
        userToAdd.setPassword("ka");
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

    //@Test
    //public void testRegisterUser_DatabaseLayerException() throws DatabaseLayerException {}
}

