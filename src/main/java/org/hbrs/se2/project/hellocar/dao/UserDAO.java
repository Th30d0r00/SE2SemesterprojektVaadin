package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.dtos.RolleDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnection;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.AccountType;
import org.hbrs.se2.project.hellocar.util.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnection;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

public class UserDAO {

    public UserDTO findUserByUseridAndPassword(String id, String password) throws DatabaseLayerException {
        // Set ResultSet to null;
        ResultSet set = null;

        // Set try-clause
        try {
            Statement statement = null;
            try {
                statement = JDBCConnection.getInstance().getStatement();
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            set = statement.executeQuery(
                    "SELECT * "
                       + "FROM carlook.user "
                       + "WHERE carlook.user.userid = \'" + id + "\'"
                         + " AND carlook.user.password = \'" + password + "\'");

            // JDBCConnection.getInstance().closeConnection();

        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler im SQL-Befehl!");
            e.setReason(Globals.Errors.SQLERROR);
            throw e;
        }
        catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler bei Datenbankverbindung!");
            e.setReason(Globals.Errors.DATABASE);
            throw e;
        }

        UserDTOImpl user = null;

        try {
            if (set.next()) {
                // Durchführung des Object-Relational-Mapping (ORM)

                user = new UserDTOImpl();
                user.setId( set.getInt(1));
                user.getStudent().setFirstname( set.getString(3) );
                user.getStudent().setLastname(set.getString(4));

                // Beziehe die Rollen eines Users:
                RolleDAO rolleDAO = new RolleDAO();
                List<RolleDTO> rollen = rolleDAO.getRolesOfUser(user);

                // Einsetzen der Rollen in ein User-Object
                user.setRoles(rollen);

                return user;

            } else {
                // Error Handling
                DatabaseLayerException e = new DatabaseLayerException("No User Could be found");
                e.setReason(Globals.Errors.NOUSERFOUND);
                throw e;
            }
        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Probleme mit der Datenbank");
            e.setReason(Globals.Errors.DATABASE);
            throw e;

        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }

    public UserDTO FindUserByEmail(String email) throws DatabaseLayerException {
        UserDTO user = new UserDTOImpl();
        // Set ResultSet to null;
        ResultSet set = null;

        // Set try-clause
        try {
            Statement statement = null;
            try {
                statement = JDBCConnection.getInstance().getStatement();
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            //Suche den user über seine email
            set = statement.executeQuery(
                    "SELECT * "
                            + "FROM collabhbrs.users "
                            + "WHERE collabhbrs.users.email = \'" + email + "\'");

            // JDBCConnection.getInstance().closeConnection();

        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler im SQL-Befehl!");
            e.setReason(Globals.Errors.SQLERROR);
            throw e;
        }
        catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler bei Datenbankverbindung!");
            e.setReason(Globals.Errors.DATABASE);
            throw e;
        }

        try {
            if (set.next()) {
                // Durchführung des Object-Relational-Mapping (ORM)
                user.setId( set.getInt("id"));
                user.setUserId(set.getString("userid"));
                user.setEmail( set.getString("email") );
                user.setPassword( set.getString("password"));
                user.setAccountType(AccountType.valueOf(set.getString("accounttype")));

                // Beziehe die Rollen eines Users:
                RolleDAO rolleDAO = new RolleDAO();
                List<RolleDTO> rollen = rolleDAO.getRolesOfUser(user);

                // Einsetzen der Rollen in ein User-Object
                user.setRoles(rollen);

                return user;

            } else {
                // Error Handling, falls kein User gefunden wird
                DatabaseLayerException e = new DatabaseLayerException("No User Could be found");
                e.setReason(Globals.Errors.NOUSERFOUND);
                throw e;
            }
        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Probleme mit der Datenbank");
            e.setReason(Globals.Errors.DATABASE);
            throw e;
        } catch (DatabaseLayerException e) {
            user = null;
            return user;
        } finally {
            JDBCConnection.getInstance().closeConnection();
        }
    }

    public boolean AddUser(UserDTO userDTO) throws DatabaseLayerException {
        boolean successfullyAddedUser = false;
        try {
            Statement statement = null;
            try {
                statement = JDBCConnection.getInstance().getStatement();
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
            }

            // Fügt einen neuen Studenten in die Datenbank ein
            if(userDTO.getStudent() != null) {
                statement.executeUpdate(
                        "INSERT INTO collabhbrs.student (firstname, lastname, birthday) " +
                                "VALUES ('" + userDTO.getStudent().getFirstname() + "', '" +
                                userDTO.getStudent().getLastname() + "', '" +
                                userDTO.getStudent().getBirthday() + "')", Statement.RETURN_GENERATED_KEYS
                );
            } else { // Fügt eine Company hinzu
                statement.executeUpdate(
                        "INSERT INTO collabhbrs.company (companyName, foundingDate, employees) " +
                                "VALUES ('" + userDTO.getCompany().getCompanyName() + "', '" +
                                userDTO.getCompany().getFoundingDate() + "', '" +
                                userDTO.getCompany().getEmployees() + "')", Statement.RETURN_GENERATED_KEYS
                );
            }

            // holt sich den automatisch generierten Primary Key des Studenten
            ResultSet keys = statement.getGeneratedKeys();
            keys.next();
            int newKey = keys.getInt(1);

            //Füge einen neuen User in die Datenbank ein
            statement.executeUpdate(
                    "INSERT INTO collabhbrs.users (userid, email, password, accounttype, student_id) " +
                            "VALUES ('" + userDTO.getUserId() + "', '" +
                            userDTO.getEmail() + "', '" +
                            userDTO.getPassword() + "', '" +
                            userDTO.getAccountType().toString() + "', " +
                            newKey + ")"
            );

            //an dieser Stelle evtl. checken ob user dann auch existiert
            successfullyAddedUser = true;

        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler im SQL-Befehl!");
            e.setReason(Globals.Errors.SQLERROR);
            throw e;
        }
        catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler bei Datenbankverbindung!");
            e.setReason(Globals.Errors.DATABASE);
            throw e;
        }
        finally {
                JDBCConnection.getInstance().closeConnection();
        }
        return successfullyAddedUser;
    }
}
