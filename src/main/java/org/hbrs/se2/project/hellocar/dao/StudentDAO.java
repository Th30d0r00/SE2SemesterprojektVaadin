package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnectionPrepared;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

import java.sql.*;
import java.time.LocalDate;

/**
 * Dient der Verwaltung von Unternehmen in der Datenbank.
 */

public class StudentDAO {
    public StudentDTO getStudentById(int studentId) throws SQLException, DatabaseLayerException {
        StudentDTO student = null;
        String sql = "SELECT * FROM collabhbrs.student WHERE id = ?";
        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sql)) {
            statement.setInt(1, studentId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    student = mapResultSetToStudent(resultSet);
                }
            }
        } catch (DatabaseLayerException e) {
            throw new RuntimeException(e);
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return student;
    }

    public boolean updateStudentProfileInDB(int studentId, String newFirstName, String newLastname,
                                            LocalDate newBirthday, int newFachsemester) throws DatabaseLayerException {
        boolean successfullyUpdatedStudent = false;
        String sql = "UPDATE collabhbrs.student SET firstname = ?, lastname = ?, birthday = ?, fachsemester = ? WHERE id = ?";

        try (PreparedStatement statement = JDBCConnectionPrepared.getInstance().getPreparedStatement(sql)) {
            statement.setString(1, newFirstName);
            statement.setString(2, newLastname);
            statement.setDate(3, Date.valueOf(newBirthday));
            statement.setInt(4, newFachsemester);
            statement.setInt(5, studentId);

            int result = statement.executeUpdate();
            if (result > 0) {
                successfullyUpdatedStudent = true;
            }
        } catch (SQLException ex) {
            throw new DatabaseLayerException("Fehler im SQL-Befehl!");
        } finally {
            JDBCConnectionPrepared.getInstance().closeConnection();
        }
        return successfullyUpdatedStudent;
    }

    private StudentDTO mapResultSetToStudent(ResultSet set) throws SQLException {
        StudentDTO student = new StudentDTOImpl();
        student.setId(set.getInt("id"));
        student.setFirstname(set.getString("firstname"));
        student.setLastname(set.getString("lastname"));
        student.setBirthday(set.getDate("birthday").toLocalDate());
        student.setFachsemester(set.getInt("fachsemester"));
        return student;
    }
}

