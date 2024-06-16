package org.hbrs.se2.project.hellocar.dtos.impl;

import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import org.hbrs.se2.project.hellocar.entities.Anzeige;
import org.hbrs.se2.project.hellocar.entities.ApplicationFile;
import org.hbrs.se2.project.hellocar.entities.Student;

import java.time.LocalDateTime;
import java.util.List;

public class ApplicationDTOImpl implements ApplicationDTO {
    private int id;
    private String motivationsschreiben;
    private LocalDateTime appliedAt;
    private String status; // z.B. versendet, eingegangen, beantwortet -> Benachrichtigung an Student bei Statusänderung
    private Anzeige stellenanzeige; // Verlinkt mit Stellenanzeige, null bei Initiativbewebrung
    private Student student; // Verlinkt mit Student
    private List<ApplicationFile> files; // Liste mit den angehängten Files
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getMotivationsschreiben() {
        return this.motivationsschreiben;
    }

    @Override
    public LocalDateTime getAppliedAt() {
        return this.appliedAt;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public Anzeige getStellenanzeige() {
        return this.stellenanzeige;
    }

    @Override
    public Student getStudent() {
        return this.student;
    }

    @Override
    public List<ApplicationFile> getFiles() {
        return this.files;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setMotivationsschreiben(String motivationsschreiben) {
        this.motivationsschreiben = motivationsschreiben;
    }

    @Override
    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void setStellenanzeige(Anzeige stellenanzeige) {
        this.stellenanzeige = stellenanzeige;
    }

    @Override
    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public void setFiles(List<ApplicationFile> files) {
        this.files = files;
    }
}
