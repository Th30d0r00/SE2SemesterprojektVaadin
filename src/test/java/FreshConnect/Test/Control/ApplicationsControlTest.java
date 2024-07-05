package FreshConnect.Test.Control;

import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.AnzeigeDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.ApplicationDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ApplicationsControlTest {

    private static ApplicationDTOImpl applicationDTO;
    private static LocalDate verfuegbarDate;
    private static LocalDateTime appliedAtDate;

    @BeforeAll
    public static void setUp() {
        // Initialize the ApplicationDTOImpl object
        verfuegbarDate = LocalDate.now();
        appliedAtDate = LocalDateTime.now();

        applicationDTO = new ApplicationDTOImpl();
        applicationDTO.setId(1);
        applicationDTO.setTelefonnummer("1234567890");
        applicationDTO.setBeschaeftigung("Vollzeit");
        applicationDTO.setVerfuegbar(verfuegbarDate);
        applicationDTO.setWohnort("Berlin");
        applicationDTO.setMotivationsschreiben("Ich bin sehr motiviert.");
        applicationDTO.setLebenslauf("Mein Lebenslauf...");
        applicationDTO.setAppliedAt(appliedAtDate);
        applicationDTO.setStatus("versendet");

        // Create and set related DTOs
        AnzeigeDTO stellenanzeige = new AnzeigeDTOImpl();
        // Set properties for stellenanzeige...
        applicationDTO.setStellenanzeige(stellenanzeige);

        StudentDTO student = new StudentDTOImpl();
        // Set properties for student...
        applicationDTO.setStudent(student);

        CompanyDTO company = new CompanyDTOImpl();
        // Set properties for company...
        applicationDTO.setCompany(company);
    }
    @Test
    void ApplicationsControl() throws DatabaseLayerException{

    }
}

