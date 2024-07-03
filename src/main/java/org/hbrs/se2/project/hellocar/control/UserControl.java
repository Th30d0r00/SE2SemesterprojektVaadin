package org.hbrs.se2.project.hellocar.control;

import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

import java.sql.SQLException;

/**
 * Implementierung der Control-Klasse UserControl
 * Hier sollen alle Methoden implementiert werden, die für die Steuerung eines Users zuständig sind
 * Dazu gehören u.a. Methoden zum Anzeigen von Anzeigen, zum Erstellen und zum Löschen von usern
 */

public class UserControl {
    private UserDAO userDAO;

    public UserControl() {
        this.userDAO = new UserDAO();
    }

    public UserDTO findUser(int userId) throws DatabaseLayerException, SQLException {
        // Anzeigen eines Users anhand der ID
        return userDAO.findUserById(userId);
    }
}
