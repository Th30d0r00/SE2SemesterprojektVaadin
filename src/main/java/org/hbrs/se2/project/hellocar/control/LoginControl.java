package org.hbrs.se2.project.hellocar.control;


import com.vaadin.flow.component.notification.Notification;
import org.hbrs.se2.project.hellocar.control.exception.DatabaseUserException;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.springframework.stereotype.Component;
import org.hbrs.se2.project.hellocar.util.Security;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class LoginControl {

    private UserDTO userDTO = null;

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
            userTmp = dao.findUserByEmail(email); //Handling der DatabaseLayerException
        }
        catch (DatabaseLayerException e){
            String error = e.getMessage();
        }
        return userTmp;

    }

    public UserDTO getCurrentUser(){
        return this.userDTO;
    }

    public boolean ValidEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
}
