package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.dao.StudentDAO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Implementierung der Control-Klasse StudentControl
 * Hier sollen alle Methoden implementiert werden, die für die Steuerung eines Studenten zuständig sind
 * Dazu gehören u.a. Methoden zum Anzeigen von Anzeigen, zum Erstellen und zum Löschen von Studenten
 */
public class StudentControl {
    private StudentDAO studentDAO;

    public StudentControl() {
        this.studentDAO = new StudentDAO();
    }

    public StudentDTO findStudent(int studentId) throws DatabaseLayerException, SQLException {
        // Anzeigen eines Studenten anhand der ID
        return studentDAO.getStudentById(studentId);
    }

    public boolean updateStudentProfile(int studentId, String newFirstName, String newLastname,
                                        LocalDate newBirthday, int newFachsemester) throws DatabaseLayerException {
        return studentDAO.updateStudentProfileInDB(studentId, newFirstName, newLastname,
                newBirthday, newFachsemester);
    }
}
