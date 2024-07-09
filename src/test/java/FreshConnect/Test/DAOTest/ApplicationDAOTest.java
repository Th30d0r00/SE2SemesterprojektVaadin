package FreshConnect.Test.DAOTest;


import org.hbrs.se2.project.hellocar.control.factories.DTOFactory;
import org.hbrs.se2.project.hellocar.dao.AnzeigeDAO;
import org.hbrs.se2.project.hellocar.dao.ApplicationDAO;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.*;

import org.hbrs.se2.project.hellocar.dtos.impl.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicationDAOTest {

    private static ApplicationDAO applicationDAO = new ApplicationDAO();
    private static UserDTO userDTOStudent = new UserDTOImpl();
    private static UserDTO userDTOCompany = new UserDTOImpl();
    private static StudentDTO studentDTO = new StudentDTOImpl();
    private static ApplicationDTO applicationDTO;
    private static AnzeigeDTO anzeigeDTO;
    private static CompanyDTO companyDTO;
    private static AnzeigeDAO anzeigeDAO = new AnzeigeDAO();
    private static UserDAO userDAO = new UserDAO();

    @BeforeAll
    public static void setup() throws DatabaseLayerException {
        studentDTO = DTOFactory.createStudent("Gustav",
                "Gans", LocalDate.of(1999, 7, 14), 4);

        companyDTO = DTOFactory.createCompany("FreshConnect",
                LocalDate.of(1999, 7, 14) , 500, "Bonn", "Eine coole Company");

        userDTOStudent = DTOFactory.createStudentUser("gustavgans@gmail.com", "test123", studentDTO);

        userDTOCompany = DTOFactory.createCompanyUser("FresheConnect@gmail.com", "test123", companyDTO);

        anzeigeDTO = DTOFactory.createAnzeigeDTO("Softwareentwickler", "Java", "Bonn",
                companyDTO, "Eine coole Stelle", LocalDateTime.now());

        applicationDTO = DTOFactory.createApplicationDTO("FreshConnect", "Tester",
                LocalDate.of(2024, 7, 14), "Berlin",
                "Ich bin sehr motiviert.", "Mein Lebenslauf...",
                LocalDateTime.now(), "versendet", anzeigeDTO, studentDTO, companyDTO, userDTOStudent);

        userDAO.addUser(userDTOStudent);
        userDAO.addUser(userDTOCompany);
        companyDTO.setId(userDAO.findUserByEmail(userDTOCompany.getEmail()).getId());
        studentDTO.setId(userDAO.findUserByEmail(userDTOStudent.getEmail()).getId());
        userDTOStudent.setId(userDAO.findUserByEmail(userDTOStudent.getEmail()).getId());
        anzeigeDAO.addAnzeige(anzeigeDTO);
        applicationDAO.addApplication(applicationDTO);
    }

    @AfterAll
    public static void teardown() throws DatabaseLayerException {
        userDAO.deleteUserProfile(userDAO.findUserByEmail(userDTOStudent.getEmail()).getId());
        userDAO.deleteUserProfile(userDAO.findUserByEmail(userDTOCompany.getEmail()).getId());
    }

    // Test if the application is added to the database
    @Test
    public void testAddApplication() throws DatabaseLayerException {
       assertTrue(applicationDAO.addApplication(applicationDTO));
    }

    //Test myApplicationList
    @Test
    public void testMyReceivedApplicationList() throws DatabaseLayerException {
        assertFalse(applicationDAO.getReceivedApplications(companyDTO.getId()).isEmpty());
    }

    //Test myApplicationList
    @Test
    public void testMySentApplicationList() throws DatabaseLayerException {
        assertFalse(applicationDAO.getMyApplications(studentDTO.getId()).isEmpty());
    }

}