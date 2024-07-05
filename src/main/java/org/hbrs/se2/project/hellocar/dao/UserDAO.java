package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.dtos.*;
import org.hbrs.se2.project.hellocar.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnectionPrepared;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.AccountType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Dient der Verwaltung von Usern in der Datenbank.
 */

public class UserDAO {

    public UserDTO findUserByUseridAndPassword(String id, String password) throws DatabaseLayerException {
        String sql = "SELECT * FROM carlook.user WHERE userid = ? AND password = ?";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sql)) {
            statement.setString(1, id);
            statement.setString(2, password);

            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    UserDTOImpl user = new UserDTOImpl();
                    user.setId(set.getInt(1));
                    user.getStudent().setFirstname(set.getString(3));
                    user.getStudent().setLastname(set.getString(4));

                    // Beziehe die Rollen eines Users
                    RolleDAO rolleDAO = new RolleDAO();
                    List<RolleDTO> rollen = rolleDAO.getRolesOfUser(user);
                    user.setRoles(rollen);

                    return user;
                } else {
                    throw new DatabaseLayerException("No User Could be found");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
    }

    public UserDTO findUserByEmail(String email) throws DatabaseLayerException {
        String sqlUser = "SELECT * FROM collabhbrs.users WHERE email = ?";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sqlUser)) {
            statement.setString(1, email);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    UserDTOImpl user = new UserDTOImpl();
                    user.setId(set.getInt("id"));
                    user.setEmail(set.getString("email"));
                    user.setSalt(set.getBytes("salt"));
                    user.setHashValue(set.getBytes("hashvalue"));
                    user.setAccountType(AccountType.valueOf(set.getString("accounttype")));

                    RolleDAO rolleDAO = new RolleDAO();
                    List<RolleDTO> rollen = rolleDAO.getRolesOfUser(user);
                    user.setRoles(rollen);

                    if (user.getAccountType() == AccountType.STUDENT) {
                        return getStudentDetails(user);
                    } else {
                        return getCompanyDetails(user);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } catch (NullPointerException e) {
            throw new DatabaseLayerException("Fehler bei Datenbankverbindung!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return null;
    }

    private UserDTO getStudentDetails(UserDTO user) throws SQLException, DatabaseLayerException {
        String sqlStudent = "SELECT * FROM collabhbrs.student WHERE id = ?";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sqlStudent)) {
            statement.setInt(1, user.getId());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    StudentDTO student = new StudentDTOImpl();
                    student.setFirstname(set.getString("firstname"));
                    student.setLastname(set.getString("lastname"));
                    student.setBirthday(set.getDate("birthday").toLocalDate());
                    user.setStudent(student);
                }
            }
        }
        return user;
    }

    private UserDTO getCompanyDetails(UserDTO user) throws SQLException, DatabaseLayerException {
        String sqlCompany = "SELECT * FROM collabhbrs.company WHERE id = ?";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sqlCompany)) {
            statement.setInt(1, user.getId());
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    CompanyDTO company = new CompanyDTOImpl();
                    company.setCompanyName(set.getString("company_name"));
                    company.setEmployees(set.getInt("employees"));
                    company.setFoundingDate(set.getDate("founding_date").toLocalDate());
                    company.setLocations(set.getString("locations"));
                    company.setDescription(set.getString("description"));
                    user.setCompany(company);
                }
            }
        }
        return user;
    }

    public boolean addUser(UserDTO userDTO) throws DatabaseLayerException {
        boolean successfullyAddedUser = false;
        String sqlUser = "INSERT INTO collabhbrs.users (email, salt, hashvalue, accounttype) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sqlUser, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, userDTO.getEmail());
            statement.setBytes(2, userDTO.getSalt());
            statement.setBytes(3, userDTO.getHashValue());
            statement.setString(4, userDTO.getAccountType().toString());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int userId = generatedKeys.getInt(1);
                    if (userDTO.getStudent() != null) {
                        addStudent(userId, userDTO.getStudent());
                    } else if (userDTO.getCompany() != null) {
                        addCompany(userId, userDTO.getCompany());
                    }
                    successfullyAddedUser = true;
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return successfullyAddedUser;
    }


    private void addStudent(int userId, StudentDTO student) throws SQLException, DatabaseLayerException {
        String sqlStudent = "INSERT INTO collabhbrs.student (id, firstname, lastname, birthday, fachsemester) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sqlStudent)) {
            statement.setInt(1, userId);
            statement.setString(2, student.getFirstname());
            statement.setString(3, student.getLastname());
            statement.setDate(4, java.sql.Date.valueOf(student.getBirthday()));
            statement.setInt(5, student.getFachsemester());
            statement.executeUpdate();
        }
    }

    private void addCompany(int userId, CompanyDTO company) throws SQLException, DatabaseLayerException {
        String sqlCompany = "INSERT INTO collabhbrs.company (id, company_name, founding_date, employees, locations, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sqlCompany)) {
            statement.setInt(1, userId);
            statement.setString(2, company.getCompanyName());
            statement.setDate(3, java.sql.Date.valueOf(company.getFoundingDate()));
            statement.setInt(4, company.getEmployees());
            statement.setString(5, company.getLocations());
            statement.setString(6, company.getDescription());
            statement.executeUpdate();
        }
    }

    public UserDTO findUserById(int id) throws DatabaseLayerException {
        String sql = "SELECT * FROM collabhbrs.users WHERE id = ?";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet set = statement.executeQuery()) {
                if (set.next()) {
                    UserDTOImpl user = new UserDTOImpl();
                    user.setId(set.getInt("id"));
                    user.setEmail(set.getString("email"));
                    user.setSalt(set.getBytes("salt"));
                    user.setHashValue(set.getBytes("hashvalue"));
                    user.setAccountType(AccountType.valueOf(set.getString("accounttype")));

                    RolleDAO rolleDAO = new RolleDAO();
                    List<RolleDTO> rollen = rolleDAO.getRolesOfUser(user);
                    user.setRoles(rollen);

                    if (user.getAccountType() == AccountType.STUDENT) {
                        return getStudentDetails(user);
                    } else {
                        return getCompanyDetails(user);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return null;
    }

    public boolean deleteUserProfile(int id) {
        String sql = "DELETE FROM collabhbrs.users WHERE id = ?";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sql)) {
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            return result > 0;
        } catch (SQLException | DatabaseLayerException e) {
            return false;
        }
    }
}
