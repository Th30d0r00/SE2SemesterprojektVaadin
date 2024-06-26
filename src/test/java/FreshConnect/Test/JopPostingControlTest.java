package FreshConnect.Test;

import org.hbrs.se2.project.hellocar.control.JobPostingControl;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.AnzeigeDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.junit.jupiter.api.Test;

public class JopPostingControlTest {

    @Test
    void testJopPostingControl() throws DatabaseLayerException {
        AnzeigeDTO anzeigeDTO = new AnzeigeDTOImpl();

        CompanyDTO companyDTO = new CompanyDTOImpl();
        companyDTO.setId(2);
        anzeigeDTO.setJobTitle("Test Job Title");
        anzeigeDTO.setStandort("Test Standort");
        anzeigeDTO.setJobType("Test Job Type");
        anzeigeDTO.setJobDescription("Test Job Description");
        // anzeigeDTO.setCompany(companyDTO);
        anzeigeDTO.setPublicationDate(java.time.LocalDateTime.now());
        JobPostingControl jobPostingControl = new JobPostingControl();
        jobPostingControl.saveJobPosting(anzeigeDTO);

    }
}
