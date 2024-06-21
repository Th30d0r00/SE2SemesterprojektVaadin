package org.hbrs.se2.project.hellocar.entities;

import java.time.LocalDateTime;
import java.util.List;

public class Application {
    private int id;
    private String telefonnummer;
    private String beschaeftigung;
    private LocalDateTime verfuegbar;
    private String wohnort;
    private String motivationsschreiben;
    private String lebenslauf;
    private LocalDateTime appliedAt;
    private String status; // z.B. versendet, eingegangen, beantwortet -> Benachrichtigung an Student bei Statusänderung
    private Anzeige stellenanzeige; // Verlinkt mit Stellenanzeige, null bei Initiativbewebrung
    private Student student; // Verlinkt mit Student
    private List<ApplicationFile> files; // Liste mit den angehängten Files (falls benötigt)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public String getBeschaeftigung() {
        return beschaeftigung;
    }

    public void setBeschaeftigung(String beschaeftigung) {
        this.beschaeftigung = beschaeftigung;
    }

    public LocalDateTime getVerfuegbar() {
        return verfuegbar;
    }

    public void setVerfuegbar(LocalDateTime verfuegbar) {
        this.verfuegbar = verfuegbar;
    }

    public String getWohnort() {
        return wohnort;
    }

    public void setWohnort(String wohnort) {
        this.wohnort = wohnort;
    }

    public String getMotivationsschreiben() {
        return motivationsschreiben;
    }

    public void setMotivationsschreiben(String motivationsschreiben) {
        this.motivationsschreiben = motivationsschreiben;
    }

    public String getLebenslauf() {
        return lebenslauf;
    }

    public void setLebenslauf(String lebenslauf) {
        this.lebenslauf = lebenslauf;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Anzeige getStellenanzeige() {
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

    public List<ApplicationFile> getFiles() {
        return files;
    }

    public void setFiles(List<ApplicationFile> files) {
        this.files = files;
    }
}
