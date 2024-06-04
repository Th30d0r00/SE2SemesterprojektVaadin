//package FreshConnect.Test;
package org.hbrs.se2.project.hellocar.views;

import org.hbrs.se2.project.hellocar.control.ManageCarControl;
import org.hbrs.se2.project.hellocar.util.AccountType;
import org.hbrs.se2.project.hellocar.views.RegistrationView;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import javax.management.Notification;
import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

public class RegistrierungTest {

    private RegistrationView registrationView;

    @BeforeEach
    public void setup() {
        // Mock ManageCarControl, da es in CheckIfFormComplete nicht direkt verwendet wird
        ManageCarControl mockCarControl = Mockito.mock(ManageCarControl.class);
        registrationView = new RegistrationView(mockCarControl);

        // Mock Notification, um UI-bezogene Probleme während des Testens zu vermeiden
        Mockito.mockStatic(Notification.class);
    }

    @Test
    public void testFormCompleteFürStudent() {
        // Formulardaten für einen Studenten setzen
        registrationView.accountType.setValue(AccountType.STUDENT);
        registrationView.email.setValue("student@test.de");
        registrationView.password.setValue("sicheresPasswort");
        registrationView.userId.setValue("studentBenutzername");
        registrationView.firstname.setValue("Max");
        registrationView.lastname.setValue("Mustermann");
        registrationView.birthday.setValue(LocalDate.of(2002, 3, 14));

        // Methode zum Testen aufrufen
        boolean result = registrationView.CheckIfFormComplete();

        // Assertions
        assertTrue(result, "Form sollte für einen Studenten vollständig sein");
    }

    @Test
    public void testFormCompleteFürFirma() {
        // Formulardaten für eine Firma setzen
        registrationView.accountType.setValue(AccountType.UNTERNEHMEN);
        registrationView.email.setValue("firma@test.de");
        registrationView.password.setValue("firmenPasswort");
        registrationView.userId.setValue("firmaBenutzername");
        registrationView.companyName.setValue("Beispiel AG");
        registrationView.foundingDate.setValue(LocalDate.of(1985, 7, 20));
        registrationView.employees.setValue("100");

        // Methode zum Testen aufrufen
        boolean result = registrationView.CheckIfFormComplete();

        // Assertions
        assertTrue(result, "Form sollte für eine Firma vollständig sein");
    }

    @Test
    public void testUnvollständigesFormularFehlendeEmail() {
        // Formulardaten ohne E-Mail setzen
        registrationView.accountType.setValue(AccountType.STUDENT);
        registrationView.password.setValue("sicheresPasswort");
        registrationView.userId.setValue("studentBenutzername");
        registrationView.firstname.setValue("Max");
        registrationView.lastname.setValue("Mustermann");
        registrationView.birthday.setValue(LocalDate.of(2002, 3, 14));

        // Methode zum Testen aufrufen
        boolean result = registrationView.CheckIfFormComplete();

        // Assertions
        assertFalse(result, "Form sollte unvollständig sein aufgrund fehlender E-Mail");
    }

    @Test
    public void testUngültigesEmailFormat() {
        // Formulardaten mit ungültiger E-Mail setzen
        registrationView.accountType.setValue(AccountType.STUDENT);
        registrationView.email.setValue("ungültige-email");
        registrationView.password.setValue("sicheresPasswort");
        registrationView.userId.setValue("studentBenutzername");
        registrationView.firstname.setValue("Max");
        registrationView.lastname.setValue("Mustermann");
        registrationView.birthday.setValue(LocalDate.of(2002, 3, 14));

        // Methode zum Testen aufrufen
        boolean result = registrationView.CheckIfFormComplete();

        // Assertions
        assertFalse(result, "Form sollte unvollständig sein aufgrund ungültigem E-Mail-Format");
    }

    @Test
    public void testZuKurzesPasswort() {
        // Formulardaten mit einem zu kurzen Passwort setzen
        registrationView.accountType.setValue(AccountType.STUDENT);
        registrationView.email.setValue("student@test.de");
        registrationView.password.setValue("kurz");
        registrationView.userId.setValue("studentBenutzername");
        registrationView.firstname.setValue("Max");
        registrationView.lastname.setValue("Mustermann");
        registrationView.birthday.setValue(LocalDate.of(2002, 3, 14));

        // Methode zum Testen aufrufen
        boolean result = registrationView.CheckIfFormComplete();

        // Assertions
        assertFalse(result, "Form sollte unvollständig sein aufgrund zu kurzem Passwort");
    }

    @Test
    public void testBenutzernameAußerhalbGrenzen() {
        // Formulardaten mit einem zu kurzen Benutzernamen setzen
        registrationView.accountType.setValue(AccountType.STUDENT);
        registrationView.email.setValue("student@test.de");
        registrationView.password.setValue("sicheresPasswort");
        registrationView.userId.setValue("usr"); // Zu kurz
        registrationView.firstname.setValue("Max");
        registrationView.lastname.setValue("Mustermann");
        registrationView.birthday.setValue(LocalDate.of(2002, 3, 14));

        // Methode zum Testen aufrufen
        boolean result = registrationView.CheckIfFormComplete();

        // Assertions
        assertFalse(result, "Form sollte unvollständig sein aufgrund zu kurzem Benutzernamen");
    }
}
