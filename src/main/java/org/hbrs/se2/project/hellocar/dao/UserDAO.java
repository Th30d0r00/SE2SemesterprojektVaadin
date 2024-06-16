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
        UserDTO user = null;
        Statement statement = null;
        ResultSet set = null;
        ResultSet set2 = null;

        try {
            statement = JDBCConnection.getInstance().getStatement();

            // Suche den User über seine Email
            String userQuery = "SELECT * FROM collabhbrs.users WHERE email = '" + email + "'";
            set = statement.executeQuery(userQuery);

            if (set.next()) {
                // Durchführung des Object-Relational-Mapping (ORM) für user
                user = new UserDTOImpl();
                user.setId(set.getInt("id"));
                user.setUserId(set.getString("userid"));
                user.setEmail(set.getString("email"));
                user.setSalt(set.getBytes("salt"));
                user.setHashValue(set.getBytes("hashvalue"));
                user.setAccountType(AccountType.valueOf(set.getString("accounttype")));

                // Beziehe die Rollen eines Users
                RolleDAO rolleDAO = new RolleDAO();
                List<RolleDTO> rollen = rolleDAO.getRolesOfUser(user);
                user.setRoles(rollen);

                // Prüfen, ob der User ein Student oder eine Company ist und dementsprechend die Attribute einfügen
                if (user.getAccountType() == AccountType.STUDENT) {
                    String studentQuery = "SELECT * FROM collabhbrs.student WHERE id = " + user.getId();
                    set2 = statement.executeQuery(studentQuery);

                    if (set2.next()) {
                        StudentDTO student = new StudentDTOImpl();
                        student.setFirstname(set2.getString("firstname"));
                        student.setLastname(set2.getString("lastname"));
                        student.setBirthday(set2.getDate("birthday").toLocalDate());
                        user.setStudent(student);
                    }
                } else {
                    String companyQuery = "SELECT * FROM collabhbrs.company WHERE id = " + user.getId();
                    set2 = statement.executeQuery(companyQuery);

                    if (set2.next()) {
                        CompanyDTO company = new CompanyDTOImpl();
                        company.setCompanyName(set2.getString("company_name"));
                        company.setEmployees(set2.getInt("employees"));
                        company.setFoundingDate(set2.getDate("founding_date").toLocalDate());
                        user.setCompany(company);
                    }
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } catch (NullPointerException ex) {
            throw new DatabaseLayerException("Fehler bei Datenbankverbindung!");
        } finally {
            // Stelle sicher, dass die ResultSet und Statement geschlossen werden
            try {
                if (set != null) {
                    set.close();
                }
                if (set2 != null) {
                    set2.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JDBCConnection.getInstance().closeConnection();
        }

        return user;
    }


    public boolean AddUser(UserDTO userDTO) throws DatabaseLayerException {
        boolean successfullyAddedUser = false;
        Statement statement = null;

        try {
            statement = JDBCConnection.getInstance().getStatement();

            // Konvertiere Byte-Array in PostgreSQL-Bytea-Format
            String salt = PGbytea.toPGString(userDTO.getSalt());
            String hashValue = PGbytea.toPGString(userDTO.getHashValue());

            // Füge einen neuen User in die Datenbank ein und erhalte den generierten Key
            String userQuery = "INSERT INTO collabhbrs.users (userid, email, salt, hashvalue, accounttype) " +
                    "VALUES ('" + userDTO.getUserId() + "', '" +
                    userDTO.getEmail() + "', '" +
                    salt + "', '" +
                    hashValue + "', '" +
                    userDTO.getAccountType().toString() + "')";

            statement.executeUpdate(userQuery, Statement.RETURN_GENERATED_KEYS);

            // Hole den automatisch generierten Primary Key des Users
            ResultSet userKeys = statement.getGeneratedKeys();
            userKeys.next();
            int userId = userKeys.getInt(1);

            // Füge einen neuen Studenten oder eine neue Company in die entsprechende Tabelle ein
            if (userDTO.getStudent() != null) {
                String studentQuery = "INSERT INTO collabhbrs.student (id, firstname, lastname, birthday) " +
                        "VALUES (" + userId + ", '" +
                        userDTO.getStudent().getFirstname() + "', '" +
                        userDTO.getStudent().getLastname() + "', '" +
                        userDTO.getStudent().getBirthday() + "')";
                statement.executeUpdate(studentQuery);
            } else {
                String companyQuery = "INSERT INTO collabhbrs.company (id, company_name, founding_date, employees) " +
                        "VALUES (" + userId + ", '" +
                        userDTO.getCompany().getCompanyName() + "', '" +
                        userDTO.getCompany().getFoundingDate() + "', " +
                        userDTO.getCompany().getEmployees() + ")";
                statement.executeUpdate(companyQuery);
            }


            successfullyAddedUser = true;

        } catch (SQLException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler im SQL-Befehl!");
            e.setReason(Globals.Errors.SQLERROR);
            throw e;
        } catch (NullPointerException ex) {
            DatabaseLayerException e = new DatabaseLayerException("Fehler bei Datenbankverbindung!");
            e.setReason(Globals.Errors.DATABASE);
            throw e;
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            JDBCConnection.getInstance().closeConnection();
        }

        return successfullyAddedUser;
    }

}
