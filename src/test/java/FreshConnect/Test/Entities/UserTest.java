package FreshConnect.Test.Entities;

import org.hbrs.se2.project.collhbrs.entities.Company;
import org.hbrs.se2.project.collhbrs.entities.Student;
import org.hbrs.se2.project.collhbrs.entities.User;
import org.hbrs.se2.project.collhbrs.util.AccountType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserTest {

    private User user;
    private Student student;
    private Company company;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1);
        user.setEmail("user@example.com");
        user.setPassword("password");
        user.setAccountType(AccountType.STUDENT);

        student = new Student();
        student.setId(101);
        student.setFirstname("John");
        student.setLastname("Doe");

        company = new Company();
        company.setId(201);
        company.setCompanyName("TechCorp");

        user.setStudentenInfos(student);
        user.setFirmenInfos(company);
        user.setRole("USER");
    }

    @Test
    public void testGetId() {
        assertEquals(1, user.getId());
    }

    @Test
    public void testGetEmail() {
        assertEquals("user@example.com", user.getEmail());
    }

    @Test
    public void testSetEmail() {
        user.setEmail("newuser@example.com");
        assertEquals("newuser@example.com", user.getEmail());
    }

    @Test
    public void testGetPassword() {
        assertEquals("password", user.getPassword());
    }

    @Test
    public void testSetPassword() {
        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    public void testGetAccountType() {
        assertEquals(AccountType.STUDENT, user.getAccountType());
    }

    @Test
    public void testGetStudentenInfos() {
        assertNotNull(user.getStudentenInfos());
        assertEquals("John", user.getStudentenInfos().getFirstname());
        assertEquals("Doe", user.getStudentenInfos().getLastname());
    }

    @Test
    public void testSetStudentenInfos() {
        Student newStudent = new Student();
        newStudent.setId(102);
        newStudent.setFirstname("Jane");
        newStudent.setLastname("Smith");
        user.setStudentenInfos(newStudent);

        assertNotNull(user.getStudentenInfos());
        assertEquals("Jane", user.getStudentenInfos().getFirstname());
        assertEquals("Smith", user.getStudentenInfos().getLastname());
    }

    @Test
    public void testGetFirmenInfos() {
        assertNotNull(user.getFirmenInfos());
        assertEquals("TechCorp", user.getFirmenInfos().getCompanyName());
    }

    @Test
    public void testSetFirmenInfos() {
        Company newCompany = new Company();
        newCompany.setId(202);
        newCompany.setCompanyName("NewTechCorp");
        user.setFirmenInfos(newCompany);

        assertNotNull(user.getFirmenInfos());
        assertEquals("NewTechCorp", user.getFirmenInfos().getCompanyName());
    }
}
