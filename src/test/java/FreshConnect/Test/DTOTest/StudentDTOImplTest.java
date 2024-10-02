package FreshConnect.Test.DTOTest;

import org.hbrs.se2.project.collhbrs.dtos.StudentDTO;
import org.hbrs.se2.project.collhbrs.dtos.impl.StudentDTOImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
@TestMethodOrder(MethodOrderer.Alphanumeric.class)

public class StudentDTOImplTest {

    private static StudentDTO studentDTO;
    private static LocalDate birthday;

    @BeforeAll
    public static void setUp() {
        // Initialize the StudentDTO object
        studentDTO = new StudentDTOImpl();
        studentDTO.setId(1);
        studentDTO.setFirstname("John");
        studentDTO.setLastname("Doe");
        birthday = LocalDate.of(2000, 1, 1);
        studentDTO.setBirthday(birthday);
        studentDTO.setFachsemester(3);
    }

    @Test
    public void atestGetId() {
        assertEquals(1, studentDTO.getId());
    }

    @Test
    public void testSetId() {
        studentDTO.setId(2);
        assertEquals(2, studentDTO.getId());
    }

    @Test
    public void atestGetFirstname() {
        assertEquals("John", studentDTO.getFirstname());
    }

    @Test
    public void testSetFirstname() {
        studentDTO.setFirstname("Jane");
        assertEquals("Jane", studentDTO.getFirstname());
    }

    @Test
    public void atestGetLastname() {
        assertEquals("Doe", studentDTO.getLastname());
    }

    @Test
    public void testSetLastname() {
        studentDTO.setLastname("Smith");
        assertEquals("Smith", studentDTO.getLastname());
    }

    @Test
    public void atestGetBirthday() {
        assertEquals(birthday, studentDTO.getBirthday());
    }

    @Test
    public void testSetBirthday() {
        LocalDate newBirthday = LocalDate.of(1995, 5, 15);
        studentDTO.setBirthday(newBirthday);
        assertEquals(newBirthday, studentDTO.getBirthday());
    }

    @Test
    public void atestGetFachsemester() {
        assertEquals(3, studentDTO.getFachsemester());
    }

    @Test
    public void testSetFachsemester() {
        studentDTO.setFachsemester(4);
        assertEquals(4, studentDTO.getFachsemester());
    }
}
