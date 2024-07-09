package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.textfield.PasswordField;
import org.hbrs.se2.project.hellocar.util.RegistrationResult;
import org.hbrs.se2.project.hellocar.control.RegistrationControl;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.hellocar.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.util.AccountType;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.util.Security;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * Ansicht zur Registrierung eines neuen Benutzers. Diese View enthält ein Formular zur Eingabe der Registrierungsdaten.
 */

@Route(value = Globals.Pages.USER_REGISTRATION)
@PageTitle("User Registration")
@CssImport("./styles/views/entercar/enter-car-view.css")
public class RegistrationView extends Div {  // 3. Form (Spezialisierung / Vererbung)

    //Formfelder & Registrierungsbutton
    ComboBox<AccountType> accountType = new ComboBox<>("Firma/Student");
    TextField email = new TextField("E-Mail");
    PasswordField password = new PasswordField("Passwort");
    TextField companyName = new TextField("Firmenname");
    DatePicker foundingDate = new DatePicker("Gründungsdatum");
    TextField employees = new TextField("Anzahl Mitarbeiter");
    TextField locations = new TextField("Standorte");
    TextField description = new TextField("Beschreiben Sie Ihr Unternehmen in 2 Sätzen");

    TextField firstname = new TextField("Vorname");
    TextField lastname = new TextField("Nachname");
    DatePicker birthday = new DatePicker("Geburtsdatum");
    TextField fachsemester = new TextField("Fachsemester");

    Button register = new Button("Register");

    private final Binder<UserDTOImpl> binder = new Binder(UserDTOImpl.class);

    //RegistrationView
    public RegistrationView() {
        addClassName("enter-car-view");
        accountType.setItems(AccountType.values());

        defaultVisibility();

        accountType.addValueChangeListener(e -> {
            if(e.getValue() == AccountType.STUDENT){
                accountType.setVisible(true);
                studentVisibility();
            }
            if(e.getValue() == AccountType.UNTERNEHMEN){
                accountType.setVisible(true);
                companyVisibility();
            }
        });

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        // Default Mapping of Cars attributes and the names of this View based on names
        // Source: https://vaadin.com/docs/flow/binding-data/tutorial-flow-components-binder-beans.html
        binder.bindInstanceFields(this); // Nr. 1 API-Methode
        clearForm();

        //Wenn RegisterButton gedrückt wird
        register.addClickListener(e -> {

            boolean formComplete = checkIfFormComplete();

            if(formComplete){
                RegistrationControl regControl = new RegistrationControl();
                UserDTO userDTO = new UserDTOImpl();

                try{
                    if(accountType.getValue() == AccountType.STUDENT){
                        fillUserDtoAsStudent(userDTO);
                    }
                    if(accountType.getValue() == AccountType.UNTERNEHMEN){
                        fillUserDtoAsCompany(userDTO);
                    }
                } catch (NumberFormatException nfe){
                    Notification.show("Fehlerhafte Eingabe beim Aufruf von FillUserDTOAsCompany / AsStudent");
                }

                RegistrationResult result = regControl.registerUser(userDTO);

                if (!result.getSuccess()) {
                    Notification.show(result.getMessage());
                } else {
                    Notification.show(result.getMessage());
                    clearForm();
                    Notification.show("Danke, dass Sie Fresh_Connect nutzen!", 3000, Notification.Position.MIDDLE);
                    UI.getCurrent().navigate( Globals.Pages.LOGIN_VIEW );
                }
            }
        });
    }

    private void clearForm() {
        binder.setBean(new UserDTOImpl());
    }

    private Component createTitle() {
        return new H3("User Registration");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(email, password, accountType, companyName, locations,
                foundingDate, employees, firstname, lastname, birthday, fachsemester, description);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(register);
        return buttonLayout;
    }

    private void defaultVisibility(){
        accountType.setVisible(true);
        password.setVisible(true);
        email.setVisible(true);

        companyName.setVisible(false);
        foundingDate.setVisible(false);
        employees.setVisible(false);
        locations.setVisible(false);
        description.setVisible(false);
        firstname.setVisible(false);
        lastname.setVisible(false);
        birthday.setVisible(false);
        fachsemester.setVisible(false);
    }

    private void studentVisibility(){
        companyName.setVisible(false);
        foundingDate.setVisible(false);
        employees.setVisible(false);
        locations.setVisible(false);
        description.setVisible(false);
        firstname.setVisible(true);
        lastname.setVisible(true);
        birthday.setVisible(true);
        fachsemester.setVisible(true);
    }

    private void companyVisibility(){
        companyName.setVisible(true);
        foundingDate.setVisible(true);
        employees.setVisible(true);
        locations.setVisible(true);
        description.setVisible(true);
        firstname.setVisible(false);
        lastname.setVisible(false);
        birthday.setVisible(false);
    }

    private void fillUserDtoAsStudent(UserDTO userDTO){
        userDTO.setSalt(Security.getSalt());
        userDTO.setHashValue(Security.getHash(password.getValue(), userDTO.getSalt()));
        userDTO.setEmail(email.getValue());
        //userDTO.setPassword(password.getValue());
        userDTO.setAccountType(accountType.getValue());

        StudentDTO studentDTO = new StudentDTOImpl();
        studentDTO.setFirstname(firstname.getValue());
        studentDTO.setLastname(lastname.getValue());
        studentDTO.setBirthday(birthday.getValue());
        studentDTO.setFachsemester(Integer.parseInt(fachsemester.getValue()));

        userDTO.setStudent(studentDTO);
    }

    private void fillUserDtoAsCompany(UserDTO userDTO){
        userDTO.setSalt(Security.getSalt());
        userDTO.setHashValue(Security.getHash(password.getValue(), userDTO.getSalt()));
        userDTO.setEmail(email.getValue());
        userDTO.setAccountType(accountType.getValue());

        CompanyDTO companyDTO = new CompanyDTOImpl();
        companyDTO.setCompanyName(companyName.getValue());
        companyDTO.setFoundingDate(foundingDate.getValue());
        companyDTO.setEmployees( Integer.parseInt(employees.getValue()));
        companyDTO.setLocations(locations.getValue());
        companyDTO.setDescription(description.getValue());
        userDTO.setCompany(companyDTO);
    }

    boolean checkIfFormComplete(){
        boolean formComplete = true;

        AccountType form_type = accountType.getValue();
        String form_mail = email.getValue();
        String form_pw = password.getValue();

        if(form_type == null){
            formComplete = false;
            Notification.show("Bitte wählen Sie ein Kundenkonto aus (Student/Unternehmen).");
        }
        if(form_mail == null || form_mail.isEmpty()){
            formComplete = false;
            Notification.show("Bitte geben Sie eine E-Mail Adresse an.");
        } else if(!validEmail(form_mail)){
            formComplete = false;
            Notification.show("Bitte geben Sie eine gültige E-Mail Adresse an.");
        }
        if (form_pw == null || form_pw.isEmpty()) {
            formComplete = false;
            Notification.show("Bitte geben Sie ein Passwort ein.");
        } else if(form_pw.length() < 8){
            formComplete = false;
            Notification.show("Ihr Passwort muss mindestens 8 Zeichen lang sein.");
        }

        if (form_type == AccountType.STUDENT) {
            String form_firstname = firstname.getValue();
            String form_lastname = lastname.getValue();
            LocalDate form_birthday = birthday.getValue();
            String form_fachsemesterStr = fachsemester.getValue();
            int form_fachsemester = 0;

            // Überprüfen, ob form_fachsemesterStr numerisch ist, bevor es geparst wird
            if (form_fachsemesterStr != null && !form_fachsemesterStr.isEmpty() && isNumeric(form_fachsemesterStr)) {
                form_fachsemester = Integer.parseInt(form_fachsemesterStr);
            } else {
                formComplete = false;
                Notification.show("Bitte geben Sie eine gültige Zahl für das Fachsemester ein.");
            }

            if (form_firstname == null || form_firstname.isEmpty()) {
                formComplete = false;
                Notification.show("Bitte geben Sie einen Vornamen ein.");
            } else if (form_firstname.length() > 64) {
                formComplete = false;
                Notification.show("Ihr Vorname darf nicht länger als 64 Zeichen sein.");
            }

            if (form_lastname == null || form_lastname.isEmpty()) {
                formComplete = false;
                Notification.show("Bitte geben Sie einen Nachnamen ein.");
            } else if (form_lastname.length() > 64) {
                formComplete = false;
                Notification.show("Ihr Nachname darf nicht länger als 64 Zeichen sein.");
            }

            if (form_fachsemester <= 0) {
                formComplete = false;
                Notification.show("Bitte geben Sie ein gültiges Fachsemester an.");
            }

            // Überprüfen, ob das Geburtsdatum mindestens 16 Jahre zurückliegt
            if (form_birthday == null || !isAtLeast16YearsOld(form_birthday)) {
                formComplete = false;
                Notification.show("Sie müssen mindestens 16 Jahre alt sein.");
            }
        }


        if (form_type == AccountType.UNTERNEHMEN) {
            String form_companyName = companyName.getValue();
            LocalDate form_foundingDate = foundingDate.getValue();
            String form_employees = employees.getValue();
            String form_locations = locations.getValue();
            String form_description = description.getValue();
            int employeeCount = 0;

            if (form_companyName == null || form_companyName.isEmpty()) {
                formComplete = false;
                Notification.show("Bitte geben Sie einen Firmennamen an.");
            } else if (form_companyName.length() > 64) {
                formComplete = false;
                Notification.show("Ihr Firmenname darf nicht länger als 64 Zeichen sein.");
            }

            if (form_employees == null || form_employees.isEmpty() || !isNumeric(form_employees)) {
                formComplete = false;
                Notification.show("Geben Sie bitte eine gültige Zahl für die Mitarbeiteranzahl ein.");
            } else {
                employeeCount = Integer.parseInt(form_employees);
                if (employeeCount <= 0) {
                    formComplete = false;
                    Notification.show("Die Mitarbeiteranzahl muss größer als 0 sein.");
                }
            }

            if (form_locations == null || form_locations.isEmpty()) {
                formComplete = false;
                Notification.show("Geben Sie mindestens einen Standort ein.");
            }

            if (form_foundingDate == null) {
                formComplete = false;
                Notification.show("Wählen Sie bitte Ihr Gründungsdatum aus.");
            }

            if (form_description == null || form_description.isEmpty() || form_description.length() > 500) {
                formComplete = false;
                Notification.show("Geben Sie eine kurze Beschreibung Ihres Unternehmens ein, die bis zu 500 Zeichen lang ist.");
            }
        }

        return formComplete;
    }

    public boolean validEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    // Hilfsmethode, um zu überprüfen, ob eine Zeichenkette numerisch ist
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Hilfsmethode, um zu überprüfen, ob ein Datum mindestens 16 Jahre zurückliegt
    private boolean isAtLeast16YearsOld(LocalDate birthdate) {
        LocalDate today = LocalDate.now();
        LocalDate thresholdDate = today.minusYears(16);
        return (birthdate.isBefore(thresholdDate) || birthdate.isEqual(thresholdDate));
    }


}
