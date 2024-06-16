package org.hbrs.se2.project.hellocar.dtos;

import org.hbrs.se2.project.hellocar.entities.Anzeige;
import org.hbrs.se2.project.hellocar.entities.ApplicationFile;
import org.hbrs.se2.project.hellocar.entities.Student;

import java.time.LocalDateTime;
import java.util.List;

public interface ApplicationDTO {
    public int getId();
    public String getMotivationsschreiben();
    public LocalDateTime getAppliedAt();
    public String getStatus();
    public Anzeige getStellenanzeige();
    public Student getStudent();
    public List<ApplicationFile> getFiles();

    public void setId(int id);
    public void setMotivationsschreiben(String motivationsschreiben);
    public void setAppliedAt(LocalDateTime appliedAt);
    public void setStatus(String status);
    public void setStellenanzeige(Anzeige stellenanzeige);
    public void setStudent(Student student);
    public void setFiles(List<ApplicationFile> files);

}
