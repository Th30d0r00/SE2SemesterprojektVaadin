package FreshConnect.Test;

import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.AccountType;
import org.hbrs.se2.project.hellocar.util.Security;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserDaoTest {

        @Test
        public void addUserAsStudentTest() throws DatabaseLayerException {

            /*
             * Es wird ein User erstellt, ihm werden die verschiedenen Attribute zugewiesen
             * */

            UserDAO dao = new UserDAO();
            UserDTO userToAdd = new UserDTOImpl();
            userToAdd.setUserId("mustermann");
            userToAdd.setEmail("mustermann@gmail.com");
            userToAdd.setPassword("mustermann1234");
            userToAdd.setSalt(Security.getSalt());
            userToAdd.setHashValue(Security.getHash(userToAdd.getPassword(),userToAdd.getSalt()));
            userToAdd.setAccountType(AccountType.valueOf("STUDENT"));

            /*
             * Es wird ein StudentDTO erstellt und dem Attribut Student des Users zugewiesen
             * */

            StudentDTO studentToAdd = new StudentDTOImpl();
            studentToAdd.setFirstname("peter");
            studentToAdd.setLastname("muster");
            LocalDate date = LocalDate.of(2020, 1, 8);
            studentToAdd.setBirthday(date);
            userToAdd.setStudent(studentToAdd);
            dao.AddUser(userToAdd);

            /*
             * Der User wird mit der Methode findUSerByEmail ausgelesen und die Attribute mit dem ursprünglich
             * erstellten User verglichen
             * */

            UserDTO readUser = dao.FindUserByEmail("mustermann@gmail.com");
            assertEquals(userToAdd.getEmail(), readUser.getEmail());
            assertEquals(userToAdd.getUserId(), readUser.getUserId());
            assertEquals(userToAdd.getAccountType(), readUser.getAccountType());

            /*
             * Es werden zum Schluss noch die Attribute des Studenten ausgelesen und verglichen
             * */

            assertEquals(userToAdd.getStudent().getFirstname(), readUser.getStudent().getFirstname());
            assertEquals(userToAdd.getStudent().getLastname(), readUser.getStudent().getLastname());
            assertEquals(userToAdd.getStudent().getBirthday(), readUser.getStudent().getBirthday());
        }

    @Test
    public void addUserAsCompanyTest() throws DatabaseLayerException {

        /*
         * Es wird ein User erstellt, ihm werden die verschiedenen Attribute zugewiesen
         * */

        UserDAO dao = new UserDAO();
        UserDTO userToAdd = new UserDTOImpl();
        userToAdd.setUserId("musterfirma");
        userToAdd.setEmail("mustermann@hotmail.com");
        userToAdd.setPassword("musterfirma1234");
        userToAdd.setSalt(Security.getSalt());
        userToAdd.setHashValue(Security.getHash(userToAdd.getPassword(),userToAdd.getSalt()));
        userToAdd.setAccountType(AccountType.valueOf("UNTERNEHMEN"));

        /*
         * Es wird ein CompanyDTO erstellt und dem Attribut Company des Users zugewiesen
         * */

        CompanyDTO companyToAdd = new CompanyDTOImpl();
        companyToAdd.setCompanyName("mustergmbh");
        companyToAdd.setEmployees(13);
        LocalDate date = LocalDate.of(2020, 1, 8);
        companyToAdd.setFoundingDate(date);
        userToAdd.setCompany(companyToAdd);
        dao.AddUser(userToAdd);

        /*
         * Der User wird mit der Methode findUSerByEmail ausgelesen und die Attribute mit dem ursprünglich
         * erstellten User verglichen
        * */

        UserDTO readUser = dao.FindUserByEmail("mustermann@hotmail.com");
        assertEquals(userToAdd.getEmail(), readUser.getEmail());
        assertEquals(userToAdd.getUserId(), readUser.getUserId());
        assertEquals(userToAdd.getAccountType(), readUser.getAccountType());

        /*
         * Es werden zum Schluss noch die Attribute des Unternehmens ausgelesen und verglichen
         * */

        assertEquals(userToAdd.getCompany().getCompanyName(), readUser.getCompany().getCompanyName());
        assertEquals(userToAdd.getCompany().getEmployees() , readUser.getCompany().getEmployees());
        assertEquals(userToAdd.getCompany().getFoundingDate(), readUser.getCompany().getFoundingDate());


    }
}
