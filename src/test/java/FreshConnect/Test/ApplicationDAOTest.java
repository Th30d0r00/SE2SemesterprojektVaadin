package FreshConnect.Test;
import org.hbrs.se2.project.hellocar.dao.ApplicationDAO;
import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import org.hbrs.se2.project.hellocar.entities.Student;
import org.hbrs.se2.project.hellocar.services.db.JDBCConnection;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationDAOTest {

    @Mock
    private JDBCConnection mockJDBCConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private ApplicationDAO applicationDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        applicationDAO = new ApplicationDAO();
        applicationDAO.setJdbcConnection(mockJDBCConnection);
    }

    @Test
    void testAddApplication_Successful() throws Exception {
        // Arrange
        ApplicationDTO applicationDTO = createSampleApplicationDTO();
        when(mockJDBCConnection.getStatement()).thenReturn(mockStatement);
        when(mockStatement.executeUpdate(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(1);
        when(mockStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1);

        // Act
        Boolean result = applicationDAO.addApplication(applicationDTO);

        // Assert
        assertTrue(result);
        assertEquals(1, applicationDTO.getId());
        verify(mockStatement).executeUpdate(contains("INSERT INTO collabhbrs.application"), eq(Statement.RETURN_GENERATED_KEYS));
        verify(mockJDBCConnection).closeConnection();
    }

    @Test
    void testAddApplication_Unsuccessful() throws Exception {
        // Arrange
        ApplicationDTO applicationDTO = createSampleApplicationDTO();
        when(mockJDBCConnection.getStatement()).thenReturn(mockStatement);
        when(mockStatement.executeUpdate(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(0);

        // Act
        Boolean result = applicationDAO.addApplication(applicationDTO);

        // Assert
        assertFalse(result);
        verify(mockStatement).executeUpdate(contains("INSERT INTO collabhbrs.application"), eq(Statement.RETURN_GENERATED_KEYS));
        verify(mockJDBCConnection).closeConnection();
    }

    @Test
    void testAddApplication_SQLExceptionThrown() throws Exception {
        // Arrange
        ApplicationDTO applicationDTO = createSampleApplicationDTO();
        when(mockJDBCConnection.getStatement()).thenReturn(mockStatement);
        when(mockStatement.executeUpdate(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenThrow(new SQLException("SQL Error"));

        // Act & Assert
        assertThrows(DatabaseLayerException.class, () -> applicationDAO.addApplication(applicationDTO));
        verify(mockJDBCConnection).closeConnection();
    }

    @Test
    void testAddApplication_NullPointerExceptionThrown() throws Exception {
        // Arrange
        ApplicationDTO applicationDTO = createSampleApplicationDTO();
        when(mockJDBCConnection.getStatement()).thenReturn(null);

        // Act & Assert
        assertThrows(DatabaseLayerException.class, () -> applicationDAO.addApplication(applicationDTO));
        verify(mockJDBCConnection).closeConnection();
    }

    private ApplicationDTO createSampleApplicationDTO() {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setMotivationsschreiben("Sample motivation");
        dto.setStatus("Pending");
        dto.setStellenanzeige(new Stellenanzeige(1));
        dto.setStudent(new Student(1));
        dto.setAppliedAt(LocalDateTime.now());
        return dto;
    }
}