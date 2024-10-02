package org.hbrs.se2.project.collhbrs.entities;

public class ApplicationFile {
    private int id;
    private String fileName; // Name der Datei, z.B. Lebenslauf
    private String fileType;
    private byte[] data;
    private Application application; // Verlinkung zu Bewerbung
}
