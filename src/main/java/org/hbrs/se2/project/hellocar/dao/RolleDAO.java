package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.RolleDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnectionPrepared;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Bereitstellung einer Schnittstelle für den Zugriff auf Rollen in der Datenbank
 * Realisierung einer CRUD-Schnittstelle (hier: nur Read (get...)) --> SE-2
 *
 */
public class RolleDAO {
    public List<RolleDTO> getRolesOfUser(UserDTO userDTO) throws DatabaseLayerException {
        List<RolleDTO> liste = new ArrayList<>();
        String sql = "SELECT * FROM collabhbrs.user_to_rolle WHERE userid = ?";

        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sql)) {
            statement.setInt(1, userDTO.getId());
            try (ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    RolleDTOImpl role = new RolleDTOImpl();
                    // Object Relation Mapping (ORM)
                    role.setBezeichnung(set.getString(2));
                    liste.add(role);
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl! Bitte den Programmierer benachrichtigen!");
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException("Fehler bei Datenbankverbindung!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return liste;
    }
}
