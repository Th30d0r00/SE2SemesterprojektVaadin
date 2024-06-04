package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.dtos.*;
import org.hbrs.se2.project.hellocar.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.StudentDTOImpl;
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
import java.util.Objects;

import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnection;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.postgresql.util.PGbytea;

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
        StudentDTO student = new StudentDTOImpl();
        CompanyDTO company = new CompanyDTOImpl();
        Statement statement = null;

        // Set ResultSet to null;
        ResultSet set;
        ResultSet set2;

        // Set try-clause
        try {
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
                // Durchführung des Object-Relational-Mapping (ORM) für user
                user.setId( set.getInt("id"));
                user.setUserId(set.getString("userid"));
                user.setEmail( set.getString("email") );
                user.setSalt(set.getBytes("salt"));
                user.setHashValue(set.getBytes("hashvalue"));
                user.setAccountType(AccountType.valueOf(set.getString("accounttype")));
                int studentId = set.getInt("student_id");
                int companyId = set.getInt("company_id");

                // Beziehe die Rollen eines Users:
                RolleDAO rolleDAO = new RolleDAO();
                List<RolleDTO> rollen = rolleDAO.getRolesOfUser(user);

                // Einsetzen der Rollen in ein User-Object
                user.setRoles(rollen);

                //prüfen ob der User ein Student oder eine Company ist und dementsprechend die Attribute einfügen

                if (user.getAccountType().name().equals("STUDENT")) {
                    set2 = statement.executeQuery(
                            "SELECT * "
                                    + "FROM collabhbrs.student "
                                    + "WHERE collabhbrs.student.id = \'" + studentId + "\'");

                    if(set2.next()) {
                        student.setFirstname(set2.getString("firstname"));
                        student.setLastname(set2.getString("lastname"));
                        student.setBirthday(set2.getDate("birthday").toLocalDate());
                        user.setStudent(student);
                    }
                } else {
                    set2 = statement.executeQuery(
                            "SELECT * "
                                    + "FROM collabhbrs.company "
                                    + "WHERE collabhbrs.company.id = \'" + companyId + "\'");

                    if(set2.next()) {
                        company.setCompanyName(set2.getString("companyname"));
                        company.setEmployees(set2.getInt("employees"));
                        company.setFoundingDate(set2.getDate("foundingdate").toLocalDate());
                        user.setCompany(company);
                    }
                }

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


            // Konvertiere Byte-Array in PostgreSQL-Bytea-Format
            String salt = PGbytea.toPGString(userDTO.getSalt());
            String hashValue = PGbytea.toPGString(userDTO.getHashValue());


            //Füge einen neuen User in die Datenbank ein
            String query = "INSERT INTO collabhbrs.users (userid, email, salt, hashvalue, accounttype) " +
                    "VALUES ('" + userDTO.getUserId() + "', '" +
                    userDTO.getEmail() + "', " +
                    "'" + salt + "', " +
                    "'" + hashValue + "', '" +
                    userDTO.getAccountType().toString() + "')";

            statement.executeUpdate(query);


            //verbinde user mit student/company (Referenz erstellen)
            if(userDTO.getStudent() != null) {
                statement.executeUpdate(
                        "UPDATE collabhbrs.users " +
                                "SET student_id = " + newKey + " " +
                                "WHERE email = '" + userDTO.getEmail() + "'"
                );
            } else {
                statement.executeUpdate(
                        "UPDATE collabhbrs.users " +
                                "SET company_id = " + newKey + " " +
                                "WHERE email = '" + userDTO.getEmail() + "'"
                );
            }

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
