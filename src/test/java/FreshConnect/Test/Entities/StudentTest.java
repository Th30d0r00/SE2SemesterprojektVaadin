package FreshConnect.Test.Entities;

import org.hbrs.se2.project.collhbrs.entities.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@TestMethodOrder(MethodOrderer.Alphanumeric.class)

public class StudentTest {

    private static Student student;
    private static LocalDate birthday;

    @BeforeAll
    public static void setUp() {
        // Initialize the Student object
        birthday = LocalDate.of(2000, 1, 1);
        student = new Student();
        student.setId(1);
        student.setFirstname("Max");
        student.setLastname("Mustermann");
        student.setBirthday(birthday);
        student.setFachsemester(3);
    }

    @Test
    public void atestGetId() {
        assertEquals(1, student.getId());
    }

    @Test
    public void atestGetFirstname() {
        assertEquals("Max", student.getFirstname());
    }

    @Test
    public void atestGetLastname() {
        assertEquals("Mustermann", student.getLastname());
    }

    @Test
    public void atestGetBirthday() {
        assertEquals(birthday, student.getBirthday());
    }

    @Test
    public void atestGetFachsemester() {
        assertEquals(3, student.getFachsemester());
    }

    @Test
    public void testSetId() {
        student.setId(2);
        assertEquals(2, student.getId());
    }

    @Test
    public void testSetFirstname() {
        student.setFirstname("Anna");
        assertEquals("Anna", student.getFirstname());
    }

    @Test
    public void testSetLastname() {
        student.setLastname("Musterfrau");
        assertEquals("Musterfrau", student.getLastname());
    }

    @Test
    public void testSetBirthday() {
        LocalDate newBirthday = LocalDate.of(1999, 12, 31);
        student.setBirthday(newBirthday);
        assertEquals(newBirthday, student.getBirthday());
    }

    @Test
    public void testSetFachsemester() {
        student.setFachsemester(4);
        assertEquals(4, student.getFachsemester());
    }
}
