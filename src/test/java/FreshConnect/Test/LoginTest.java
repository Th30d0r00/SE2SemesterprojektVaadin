package FreshConnect.Test;

/*
import com.vaadin.flow.component.notification.Notification;
import org.hbrs.se2.project.hellocar.control.LoginControl;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.repository.UserRepository;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Security;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDTO mockUserDTO;

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private LoginControl loginControl;

    @BeforeEach
    public void setUp() {
        loginControl = new LoginControl();
        loginControl.repository = userRepository;
    }

    @Test
    public void testAuthenticateWithHashErfolg() throws DatabaseLayerException, DatabaseUserException {
        // Mock-Verhalten festlegen
        when(userDAO.FindUserByEmail(anyString())).thenReturn(mockUserDTO);
        when(Security.testHash(anyString(), anyString(), anyString())).thenReturn(true);

        // Methode aufrufen
        boolean result = loginControl.authenticateWithHash("test@example.com", "password");

        // Ergebnisse überprüfen
        assertTrue(result);
        assertEquals(mockUserDTO, loginControl.getCurrentUser());
    }

    @Test
    public void testAuthenticateWithHashFehler() throws DatabaseLayerException, DatabaseUserException {
        // Mock-Verhalten festlegen
        when(userDAO.FindUserByEmail(anyString())).thenReturn(null);

        // Methode aufrufen
        boolean result = loginControl.authenticateWithHash("test@example.com", "password");

        // Ergebnisse überprüfen
        assertFalse(result);
        assertNull(loginControl.getCurrentUser());
    }

    @Test
    public void testGetUserWithJDBCErfolg() throws DatabaseUserException {
        // Mock-Verhalten festlegen
        when(userDAO.FindUserByEmail(anyString())).thenReturn(mockUserDTO);

        // Methode aufrufen
        UserDTO userDTO = loginControl.getUserWithJDBC("test@example.com");

        // Ergebnisse überprüfen
        assertEquals(mockUserDTO, userDTO);
    }

    @Test
    public void testGetUserWithJDBCFehler() throws DatabaseUserException {
        // Mock-Verhalten festlegen
        when(userDAO.FindUserByEmail(anyString())).thenThrow(new DatabaseLayerException("Fehler"));

        // Methode aufrufen und Ausnahme abfangen
        assertThrows(DatabaseUserException.class, () -> {
            loginControl.getUserWithJDBC("test@example.com");
        });
    }

    @Test
    public void testGetCurrentUser() {
        // Mock-Verhalten festlegen
        loginControl.userDTO = mockUserDTO;

        // Methode aufrufen
        UserDTO currentUser = loginControl.getCurrentUser();

        // Ergebnisse überprüfen
        assertEquals(mockUserDTO, currentUser);
    }
}
*/

