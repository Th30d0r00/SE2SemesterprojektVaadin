/*package FreshConnect.Test;

import org.hbrs.se2.project.hellocar.dao.AnzeigeDAO;
import org.hbrs.se2.project.hellocar.dao.ApplicationDAO;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.entities.Company;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationDAOTest {

    @Mock
    private ResultSet mockResultSet;

    @Mock
    private UserDAO mockUserDAO;

    @Mock
    private AnzeigeDAO mockAnzeigeDAO;

    private ApplicationDAO applicationDAO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        applicationDAO = new ApplicationDAO();
        applicationDAO.setUserDAO(mockUserDAO);
        applicationDAO.setAnzeigeDAO(mockAnzeigeDAO);
    }

    @Test
    void testMapResultSetToApplication_FullApplication() throws SQLException, DatabaseLayerException {
        // Arrange
        setupMockResultSet(true);
        setupMockDAOs();

        // Act
        ApplicationDTO result = applicationDAO.mapResultSetToApplication(mockResultSet);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("123456789", result.getTelefonnummer());
        assertEquals("Student", result.getBeschaeftigung());
        assertEquals(LocalDate.of(2024, 1, 1), result.getVerfuegbar());
        assertEquals("Berlin", result.getWohnort());
        assertEquals("Motivation", result.getMotivationsschreiben());
        assertEquals("CV", result.getLebenslauf());
        assertEquals(LocalDate.of(2023, 7, 1).atStartOfDay(), result.getAppliedAt());
        assertEquals("Pending", result.getStatus());
        assertNotNull(result.getCompany());
        assertNotNull(result.getStudent());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("John", result.getFirstname());
        assertEquals("Doe", result.getLastname());
        assertEquals(3, result.getFachsemester());
        assertEquals(LocalDate.of(2000, 1, 1), result.getBirthday());
        assertNotNull(result.getStellenanzeige());
        assertEquals("Software Developer", result.getJobTitel());
        assertEquals("Berlin", result.getStandort());

        verify(mockResultSet, times(11)).getInt(anyString());
        verify(mockResultSet, times(6)).getString(anyString());
        verify(mockResultSet, times(2)).getDate(anyString());
        verify(mockUserDAO, times(3)).findUserById(anyInt());
        verify(mockAnzeigeDAO, times(2)).findAnzeigeById(anyInt());
    }

    @Test
    void testMapResultSetToApplication_InitiativApplication() throws SQLException, DatabaseLayerException {
        // Arrange
        setupMockResultSet(false);
        setupMockDAOs();

        // Act
        ApplicationDTO result = applicationDAO.mapResultSetToApplication(mockResultSet);

        // Assert
        assertNotNull(result);
        assertEquals("Initiativbewerbung", result.getJobTitel());
        assertEquals("-", result.getStandort());
        assertNull(result.getStellenanzeige());

        verify(mockResultSet, times(10)).getInt(anyString());
        verify(mockResultSet, times(6)).getString(anyString());
        verify(mockResultSet, times(2)).getDate(anyString());
        verify(mockUserDAO, times(3)).findUserById(anyInt());
        verify(mockAnzeigeDAO, never()).findAnzeigeById(anyInt());
    }

    @Test
    void testMapResultSetToApplication_SQLExceptionThrown() throws SQLException {
        // Arrange
        when(mockResultSet.getInt("id")).thenThrow(new SQLException("SQL Error"));

        // Act & Assert
        assertThrows(SQLException.class, () -> applicationDAO.mapResultSetToApplication(mockResultSet));
    }

    private void setupMockResultSet(boolean withStellenanzeige) throws SQLException {
        when(mockResultSet.getInt("id")).thenReturn(1);
        when(mockResultSet.getString("telefonnummer")).thenReturn("123456789");
        when(mockResultSet.getString("beschaeftigung")).thenReturn("Student");
        when(mockResultSet.getDate("verfuegbar")).thenReturn(Date.valueOf(LocalDate.of(2024, 1, 1)));
        when(mockResultSet.getString("wohnort")).thenReturn("Berlin");
        when(mockResultSet.getString("motivationsschreiben")).thenReturn("Motivation");
        when(mockResultSet.getString("lebenslauf")).thenReturn("CV");
        when(mockResultSet.getDate("applied")).thenReturn(Date.valueOf(LocalDate.of(2023, 7, 1)));
        when(mockResultSet.getString("status")).thenReturn("Pending");
        when(mockResultSet.getInt("company_id")).thenReturn(1);
        when(mockResultSet.getInt("student_id")).thenReturn(2);
        if (withStellenanzeige) {
            when(mockResultSet.getInt("stellenanzeige_id")).thenReturn(3);
        }
    }

    private void setupMockDAOs() throws DatabaseLayerException {
        Company mockCompany = mock(Company.class);
        when(mockUserDAO.findUserById(1)).thenReturn(mockCompany);

        StudentDTO mockStudent = mock(StudentDTO.class);
        when(mockUserDAO.findUserById(2)).thenReturn(mockStudent);
        when(mockStudent.getEmail()).thenReturn("test@example.com");
        when(mockStudent.getFirstname()).thenReturn("John");
        when(mockStudent.getLastname()).thenReturn("Doe");
        when(mockStudent.getFachsemester()).thenReturn(3);
        when(mockStudent.getBirthday()).thenReturn(LocalDate.of(2000, 1, 1));

        AnzeigeDTO mockAnzeige = mock(AnzeigeDTO.class);
        when(mockAnzeigeDAO.findAnzeigeById(3)).thenReturn(mockAnzeige);
        when(mockAnzeige.getJobTitle()).thenReturn("Software Developer");
        when(mockAnzeige.getStandort()).thenReturn("Berlin");
    }
}*/