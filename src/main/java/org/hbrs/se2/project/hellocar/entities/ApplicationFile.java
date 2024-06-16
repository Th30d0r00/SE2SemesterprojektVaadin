package org.hbrs.se2.project.hellocar.entities;

import java.time.LocalDateTime;

public class ApplicationFile {
    private int id;
    private String fileName; // Name der Datei, z.B. Lebenslauf
    private String fileType;
    private byte[] data;
    private Application application; // Verlinkung zu Bewerbung
}
