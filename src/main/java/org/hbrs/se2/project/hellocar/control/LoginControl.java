package org.hbrs.se2.project.hellocar.control;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.NavigationTrigger;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.hbrs.se2.project.hellocar.util.Security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class LoginControl {


    private UserDTO userDTO = null;

    /*
    public boolean authentificate(String username, String password ) throws DatabaseUserException {
        // Standard: User wird mit Spring JPA ausgelesen (Was sind die Vorteile?)
        // UserDTO tmpUser = this.getUserWithJPA( username , password );

        // Alternative: Auslesen des Users mit JDBC (Was sind die Vorteile bzw. Nachteile?)
        UserDTO tmpUser = this.getUserWithJDBC( username , password );

        if ( tmpUser == null ) {
            // ggf. hier ein Loggin einfügen
            return false;
        }
        this.userDTO = tmpUser;
        return true;
    }
    */
    /**
     * Vergleicht Hash des eingegebenen Passworts mit dem Passwort-Hash in der Datenbank
     * Dazu muss vom UserDTO der Salt und Hashwert aus der DB geliefert werden
     * @throws DatabaseLayerException
     * Julian N
     */
    public boolean authenticateWithHash(String email, String password) throws DatabaseLayerException {
        try {
            if (!ValidEmail(email)) {
                Notification.show("Bitte geben Sie eine gültige E-Mail-Adresse ein.");
                return false;
            }
            UserDTO tmpUser = this.getUserWithJDBC(email);
            if (tmpUser != null) {
                if(Security.testHash((password), tmpUser.getSalt(), tmpUser.getHashValue())){
                    this.userDTO = tmpUser;
                    return true;
                }
            }
            else{
                Notification.show("Ihre E-Mail-Adresse oder Passwort ist falsch.");
            }
        } catch (DatabaseUserException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    /**
     * User nur mit E-Mail-Adresse finden
     * @param email
     * @return userDTO
     * @throws DatabaseUserException
     * Julian N
     */
    public UserDTO getUserWithJDBC( String email) throws DatabaseUserException {
        UserDTO userTmp = null;
        UserDAO dao = new UserDAO();

        try {
            userTmp = dao.FindUserByEmail(email); //Handling der DatabaseLayerException
        }
        catch (DatabaseLayerException e){
            String error = e.getMessage();
        }
        /*
        catch ( DatabaseLayerException e) {

            // Analyse und Umwandlung der technischen Errors in 'lesbaren' Darstellungen
            // Durchreichung und Behandlung der Fehler (Chain Of Responsibility Pattern (SE-1))
            String reason = e.getReason();

            if (reason.equals(Globals.Errors.NOUSERFOUND)) {
                return userTmp;
                // throw new DatabaseUserException("No User could be found! Please check your credentials!");
            }
            else if ( reason.equals((Globals.Errors.SQLERROR))) {
                throw new DatabaseUserException("There were problems with the SQL code. Please contact the developer!");
            }
            else if ( reason.equals((Globals.Errors.DATABASE ) )) {
                throw new DatabaseUserException("A failure occured while trying to connect to database with JDBC. " +
                        "Please contact the admin");
            }
            else {
                throw new DatabaseUserException("A failure occured while");
            }

        }*/
        return userTmp;

    }

    public UserDTO getCurrentUser(){
        return this.userDTO;
    }

    private UserDTO getUserWithJDBC( String username , String password ) throws DatabaseUserException {
        UserDTO userTmp = null;
        UserDAO dao = new UserDAO();
        try {
            userDTO = dao.findUserByUseridAndPassword( username , password );
        }
        catch ( DatabaseLayerException e) {

            // Analyse und Umwandlung der technischen Errors in 'lesbaren' Darstellungen
            // Durchreichung und Behandlung der Fehler (Chain Of Responsibility Pattern (SE-1))
            String reason = e.getReason();

            if (reason.equals(Globals.Errors.NOUSERFOUND)) {
                return userTmp;
                // throw new DatabaseUserException("No User could be found! Please check your credentials!");
            }
            else if ( reason.equals((Globals.Errors.SQLERROR))) {
                throw new DatabaseUserException("There were problems with the SQL code. Please contact the developer!");
            }
            else if ( reason.equals((Globals.Errors.DATABASE ) )) {
                throw new DatabaseUserException("A failure occured while trying to connect to database with JDBC. " +
                        "Please contact the admin");
            }
            else {
                throw new DatabaseUserException("A failure occured while");
            }

        }
        return userDTO;
    }
    public boolean ValidEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
}
