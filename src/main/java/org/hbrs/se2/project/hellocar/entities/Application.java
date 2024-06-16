package org.hbrs.se2.project.hellocar.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.List;

public class Application {
    private int id;
    private String motivationsschreiben;
    private LocalDateTime appliedAt;
    private String status; // z.B. versendet, eingegangen, beantwortet -> Benachrichtigung an Student bei Statusänderung
    private Anzeige stellenanzeige; // Verlinkt mit Stellenanzeige, null bei Initiativbewebrung
    private Student student; // Verlinkt mit Student
    private List<ApplicationFile> files; // Liste mit den angehängten Files

    @Id
    @GeneratedValue
    @Column(name = "ID")
    public int getID(){return id;}

    public void setID(int id){ this.id = id;}

    @Basic
    @Column(name = "Motivationsschreiben")
    public String getMotivationsschreiben(){ return motivationsschreiben; }

    public void setMotivationsschreiben(String motivationsschreiben){
        this.motivationsschreiben = motivationsschreiben;
    }

    @Basic
    @Column(name = "Veröffentlichung")
    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
    @Basic
    @Column(name = "Status")
    public String getstatus(){return status;}

    public void setStatus(String status){
        this.status = status;
    }

    public Anzeige getStellenanzeige(){
        return stellenanzeige;
    }

    public void setStellenanzeige(Anzeige stellenanzeige) {
        this.stellenanzeige = stellenanzeige;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
