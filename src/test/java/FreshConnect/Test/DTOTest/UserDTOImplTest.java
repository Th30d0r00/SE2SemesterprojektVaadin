package FreshConnect.Test.DTOTest;

import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.RolleDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.util.AccountType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@TestMethodOrder(MethodOrderer.Alphanumeric.class)

public class UserDTOImplTest {

    private static UserDTOImpl userDTO;
    private static StudentDTOImpl student;
    private static CompanyDTOImpl company;
    private static List<RolleDTO> roles;

    @BeforeAll
    public static void setUp() {
        userDTO = new UserDTOImpl();
        userDTO.setId(1);
        userDTO.setEmail("user@example.com");
        userDTO.setPassword("password");
        userDTO.setAccountType(AccountType.STUDENT);

        student = new StudentDTOImpl();
        student.setId(101);
        student.setFirstname("John");
        student.setLastname("Doe");

        company = new CompanyDTOImpl();
        company.setId(201);
        company.setCompanyName("TechCorp");

        userDTO.setStudent(student);
        userDTO.setCompany(company);
        userDTO.setRoles(roles);
    }

    @Test
    public void testGetId() {
        assertEquals(1, userDTO.getId());
    }

    @Test
    public void testGetEmail() {
        assertEquals("user@example.com", userDTO.getEmail());
    }

    @Test
    public void testSetEmail() {
        userDTO.setEmail("newuser@example.com");
        assertEquals("newuser@example.com", userDTO.getEmail());
    }

    @Test
    public void atestGetPassword() {
        assertEquals("password", userDTO.getPassword());
    }

    @Test
    public void testSetPassword() {
        userDTO.setPassword("newpassword");
        assertEquals("newpassword", userDTO.getPassword());
    }

    @Test
    public void testGetAccountType() {
        assertEquals(AccountType.STUDENT, userDTO.getAccountType());
    }

    /*@Test
    public void testSetAccountType() {
        userDTO.setAccountType(AccountType.COMPANY);
        assertEquals(AccountType.COMPANY, userDTO.getAccountType());
    }*/

    @Test
    public void testGetStudent() {
        assertNotNull(userDTO.getStudent());
        assertEquals("John", userDTO.getStudent().getFirstname());
        assertEquals("Doe", userDTO.getStudent().getLastname());
    }

    @Test
    public void testSetStudent() {
        StudentDTO newStudent = new StudentDTOImpl();
        newStudent.setId(102);
        newStudent.setFirstname("Jane");
        newStudent.setLastname("Smith");
        userDTO.setStudent(newStudent);

        assertNotNull(userDTO.getStudent());
        assertEquals("Jane", userDTO.getStudent().getFirstname());
        assertEquals("Smith", userDTO.getStudent().getLastname());
    }

    @Test
    public void atestGetCompany() {
        assertNotNull(userDTO.getCompany());
        assertEquals("TechCorp", userDTO.getCompany().getCompanyName());
    }

    @Test
    public void testSetCompany() {
        CompanyDTO newCompany = new CompanyDTOImpl();
        newCompany.setId(202);
        newCompany.setCompanyName("NewTechCorp");
        userDTO.setCompany(newCompany);

        assertNotNull(userDTO.getCompany());
        assertEquals("NewTechCorp", userDTO.getCompany().getCompanyName());
    }

}

