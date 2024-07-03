package org.hbrs.se2.project.hellocar.dao;

import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnection;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentDAO {
    public StudentDTO getStudentById(int studentId) throws SQLException {
        StudentDTO student = null;
        String sql = "SELECT * FROM collabhbrs.student WHERE id = " + studentId;
        try (Statement statement = JDBCConnection.getInstance().getStatement();) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                student = mapResultSetToStudent(resultSet);
            }
        } catch (DatabaseLayerException e) {
            throw new RuntimeException(e);
        }
        return student;
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

