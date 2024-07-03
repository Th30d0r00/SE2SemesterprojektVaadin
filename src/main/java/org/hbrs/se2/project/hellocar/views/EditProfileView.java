/*
package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.demo.registration.RegistrationResult;
import org.hbrs.se2.project.hellocar.control.RegistrationControl;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.CompanyDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.UserDTOImpl;
import org.hbrs.se2.project.hellocar.util.AccountType;
import org.hbrs.se2.project.hellocar.util.Globals;

import java.time.LocalDate;

@Route(value = "editprofile" )
@PageTitle("Edit Profile")
public class EditProfileView extends Div {
    private final UserDAO userDAO = new UserDAO();
    private UserDTO currentUser;

    //Attribute für Company
    TextField changeCompanyName = new TextField("Firmenname");
    DatePicker changeFoundingDate = new DatePicker("Gründungsdatum");
    TextField changeEmployees = new TextField("Anzahl Mitarbeiter");
    TextField changeLocations = new TextField("Standorte");
    TextField changeDescription = new TextField("Beschreiben Sie Ihr Unternehmen in 2 Sätzen");

    //Attribute für Student
    TextField changeFirstname = new TextField("Vorname");
    TextField changeLastname = new TextField("Nachname");
    DatePicker changeBirthday = new DatePicker("Geburtsdatum");
    TextField changeFachsemester = new TextField("Fachsemester");

    Button changeButton = new Button("Änderungen übernehmen");
    Button backButton = new Button("Abbrechen");

    private final Binder<UserDTOImpl> binder = new Binder(UserDTOImpl.class);

    AccountType accountType;
    public EditProfileView() {
        currentUser = (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
        addClassName("edit-profile-view");
        accountType = currentUser.getAccountType();
        if (accountType == AccountType.STUDENT) {
            StudentVisibility();
        } else if (accountType == AccountType.UNTERNEHMEN) {
            CompanyVisibility();
        }

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        binder.bindInstanceFields(this); // Nr. 1 API-Methode
        clearForm();

        //Wenn changeButton gedrückt wird
        changeButton.addClickListener(e -> {

            boolean formComplete = CheckIfFormComplete();

            if(formComplete){
                RegistrationControl regControl = new RegistrationControl();
                StudentDTO studentDTO = new StudentDTOImpl();
                CompanyDTO companyDTO = new CompanyDTOImpl();

                try{
                    if(accountType == AccountType.STUDENT){
                        fillStudentDTOUpdate(studentDTO);
                    }
                    if(accountType == AccountType.UNTERNEHMEN){
                        fillCompanyDTOUpdate(companyDTO);
                    }
                } catch (NumberFormatException nfe){
                    Notification.show("Irgendwo fehlerhafte Eingabe");
                }

                RegistrationResult result = regControl.registerUser(userDTO);

                if (!result.getSuccess()) {
                    Notification.show(result.getMessage());
                } else {
                    Notification.show(result.getMessage());
                    clearForm();
                    UI.getCurrent().navigate( Globals.Pages.LOGIN_VIEW );
                }
            }
        });

        //Wenn backButton gedrückt wird
        backButton.addClickListener(e -> {

        });
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        changeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(changeButton);
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(backButton);
        return buttonLayout;
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(changeCompanyName, changeLocations,
                changeFoundingDate, changeEmployees, changeFirstname, changeLastname,
                changeBirthday, changeFachsemester, changeDescription);
        return formLayout;
    }

    private Component createTitle() {
        return new H3("Profil bearbeiten");
    }

    private void CompanyVisibility() {
        changeCompanyName.setVisible(true);
        changeFoundingDate.setVisible(true);
        changeEmployees.setVisible(true);
        changeLocations.setVisible(true);
        changeDescription.setVisible(true);
        changeFirstname.setVisible(false);
        changeLastname.setVisible(false);
        changeBirthday.setVisible(false);
    }

    private void StudentVisibility() {
        changeCompanyName.setVisible(false);
        changeFoundingDate.setVisible(false);
        changeEmployees.setVisible(false);
        changeLocations.setVisible(false);
        changeDescription.setVisible(false);
        changeFirstname.setVisible(true);
        changeLastname.setVisible(true);
        changeBirthday.setVisible(true);
    }

    private void clearForm() {
        binder.setBean(new UserDTOImpl());
    }

    boolean CheckIfFormComplete(){
        boolean formComplete = true;

        if (accountType == AccountType.STUDENT) {
            String form_firstname = changeFirstname.getValue();
            String form_lastname = changeLastname.getValue();
            LocalDate form_birthday = changeBirthday.getValue();
            String form_fachsemesterStr = changeFachsemester.getValue();
            int form_fachsemester = 0;

            // Überprüfen, ob form_fachsemesterStr numerisch ist, bevor es geparst wird
            if (form_fachsemesterStr != null && !form_fachsemesterStr.isEmpty() && isNumeric(form_fachsemesterStr)) {
                form_fachsemester = Integer.parseInt(form_fachsemesterStr);
            } else {
                // Fachsemester darf auch null sein
                formComplete = false;
                Notification.show("Bitte geben Sie eine gültige Zahl für das Fachsemester ein.");
            }

            if (form_firstname != null) {
                if (form_firstname.length() > 64) {
                    formComplete = false;
                    Notification.show("Ihr Vorname darf nicht länger als 64 Zeichen sein.");
                }
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
            if (form_birthday != null) {
                if(!isAtLeast16YearsOld(form_birthday)) {
                    formComplete = false;
                    Notification.show("Sie müssen mindestens 16 Jahre alt sein.");
                }
            }
        }


        if (accountType == AccountType.UNTERNEHMEN) {
            String form_companyName = changeCompanyName.getValue();
            String form_employees = changeEmployees.getValue();
            int employeeCount = 0;

            if (form_companyName != null) {
                if (form_companyName.length() > 64) {
                    formComplete = false;
                    Notification.show("Ihr Firmenname darf nicht länger als 64 Zeichen sein.");
                }
            }

            if (form_employees != null) {
                employeeCount = Integer.parseInt(form_employees);
                if (employeeCount <= 0) {
                    formComplete = false;
                    Notification.show("Die Mitarbeiteranzahl muss größer als 0 sein.");
                }
            }

        }

        return formComplete;
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

    private void fillStudentDTOUpdate(StudentDTO studentDTO) {
        studentDTO.setFirstname(changeFirstname.getValue());
        studentDTO.setLastname(changeLastname.getValue());
        studentDTO.setBirthday(changeBirthday.getValue());
        studentDTO.setFachsemester(Integer.parseInt(changeFachsemester.getValue()));
    }
    private void fillCompanyDTOUpdate(CompanyDTO companyDTO) {

    }
}
*/