
package FreshConnect.Test;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;
import org.hbrs.se2.project.hellocar.control.RegistrationControl;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO; //
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
import java.util.UUID;

public class RegistrationControlTest {
    private RegistrationControl registrationControl;

    @BeforeAll
    public static void setUp() {

        //RegistrationControl registrationControl = new RegistrationControl(); //

    }

    @Test
    public void testRegisterUser_Success() throws DatabaseLayerException {
        //create new User with random value
        RegistrationControl registrationControl = new RegistrationControl(); //
        //generate random string
        String email2 = UUID.randomUUID().toString();

        UserDTO userToAd = new UserDTOImpl();
        userToAd.setEmail(email2);
        userToAd.setPassword("kakaaaddDdad1234");
        userToAd.setSalt(Security.getSalt());
        userToAd.setHashValue(Security.getHash(userToAd.getPassword(),userToAd.getSalt()));
        userToAd.setAccountType(AccountType.valueOf("STUDENT"));
        StudentDTO studentToAdd2 = new StudentDTOImpl();
        studentToAdd2.setFirstname("peter");
        studentToAdd2.setLastname("muuster");
        LocalDate date = LocalDate.of(2020, 4, 8);
        studentToAdd2.setBirthday(date);
        userToAd.setStudent(studentToAdd2);
        //
        //register user2
        RegistrationResult result = registrationControl.registerUser(userToAd);
        //if user is already registered an Assertion Error will be thrown in
         assertTrue(result.getSuccess()); //
        assertEquals("User successfully registered.", result.getMessage());
         /*try{ //
            assertTrue(result.getSuccess()); //
            assertEquals("User successfully registered.", result.getMessage()); //
            return;
        } catch (AssertionError e) { //if user is already registered/
        }/
        //if user is already registered we will test if the user is already registered
        assertFalse(result.getSuccess());
        assertEquals(("User with email "+ userToAdd.getEmail() + " already exists"), result.getMessage());*/
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


        assertFalse(result.getSuccess()); //get
        assertEquals(("User with email "+ userToAdd.getEmail() + " already exists"), result.getMessage());


    }

    @Test
    public void testRegisterUser_AddUserFails() throws DatabaseLayerException {
        //create new User with random values
        RegistrationControl registrationControl = new RegistrationControl();

        String email = UUID.randomUUID().toString();
        UserDTO userToAdd = new UserDTOImpl();
        userToAdd.setEmail(email);
        userToAdd.setPassword("ka");
        userToAdd.setSalt(Security.getSalt());
        userToAdd.setHashValue(Security.getHash(userToAdd.getPassword(),userToAdd.getSalt()));
        userToAdd.setAccountType(AccountType.valueOf("STUDENT"));

        StudentDTO studentToAdd = new StudentDTOImpl();
        studentToAdd.setFirstname("peter");
        studentToAdd.setLastname("muster");
        LocalDate date = LocalDate.of(2020, 1, 8);
        studentToAdd.setBirthday(date);
        //userToAdd.setStudent(studentToAdd);

        //register user
        //RegistrationResult result = registrationControl.registerUser(userToAdd);
        /*try{
            RegistrationResult result = registrationControl.registerUser(userToAdd);
            //System.out.println(result.getMessage());
        }catch(DatabaseLayerException e){
            e.printStackTrace();
        }*/

        //if user is already registered an Assertion Error will be thrown

        /*try{
            assertTrue(result.getSuccess());
            assertEquals("User successfully registered.", result.getMessage());

            return;
        } catch (AssertionError e) { //if user is already registered
            e.printStackTrace();
        }
        //if user is already registered we will test if the user is already registered
        assertFalse(result.getSuccess());
        assertEquals(("User with email "+ userToAdd.getEmail() + " already exists"), result.getMessage());*/
    }
    //@Test
    //public void testRegisterUser_DatabaseLayerException() throws DatabaseLayerException {}
}

