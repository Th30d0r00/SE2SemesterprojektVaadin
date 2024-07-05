package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.hellocar.control.CompanyControl;
import org.hbrs.se2.project.hellocar.control.StudentControl;
import org.hbrs.se2.project.hellocar.control.UserControl;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.AccountType;
import org.hbrs.se2.project.hellocar.util.Globals;

import java.sql.SQLException;
import java.time.LocalDate;

@Route(value = Globals.Pages.EDIT_PROFILE, layout = AppView.class)
@PageTitle("Profil bearbeiten")
@CssImport("./styles/views/editprofileview/edit-profile-view.css")
public class EditProfileView extends Div {
    private StudentControl studentControl = new StudentControl();
    private CompanyControl companyControl = new CompanyControl();
    private UserControl userControl = new UserControl();
    private final UserDTO currentUser;
    private StudentDTO currentStudent;
    private CompanyDTO currentCompany;

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
    Button deleteButton = new Button("Mein Profil löschen");

    //private final Binder<UserDTOImpl> binder = new Binder(UserDTOImpl.class);

    AccountType accountType;

    public EditProfileView() {
        addClassName("editprofileview");
        this.studentControl = new StudentControl();
        this.companyControl = new CompanyControl();
        this.userControl = new UserControl();

        currentUser = (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
        accountType = currentUser.getAccountType();

        if (accountType == AccountType.STUDENT) {
            StudentVisibility();
        } else if (accountType == AccountType.UNTERNEHMEN) {
            CompanyVisibility();
        }

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        //Was macht der Binder?
        //binder.bindInstanceFields(this); // Nr. 1 API-Methode
        //clearForm();

        //Wenn changeButton gedrückt wird
        changeButton.addClickListener(e -> {

            boolean formComplete = checkIfFormComplete();
            boolean successResult = false;

            if(formComplete){
                try{
                    // Die aktualisierten Eingaben werden an die Control übergeben, mit Verweis auf den betroffenen User
                    if(accountType == AccountType.STUDENT){
                        this.currentStudent = studentControl.findStudent(currentUser.getId());
                        successResult = studentControl.updateStudentProfile(currentUser.getId(), changeFirstname.getValue(),
                                changeLastname.getValue(),changeBirthday.getValue(),
                                Integer.parseInt(changeFachsemester.getValue()));
                    }
                    if(accountType == AccountType.UNTERNEHMEN){
                        this.currentCompany = companyControl.findCompany(currentUser.getId());
                        successResult = companyControl.updateCompanyProfile(currentUser.getId(), changeCompanyName.getValue(),
                                changeFoundingDate.getValue(), Integer.parseInt(changeEmployees.getValue()),
                                changeLocations.getValue(), changeDescription.getValue());
                    }
                } catch (NumberFormatException nfe){
                    Notification.show("Fehlerhafte Eingabe bei der Übergabe vom Profilupdate an die Control");
                } catch (SQLException ex) {
                    Notification.show("Fehler beim Abruf des aktuellen Student bzw. Company");
                } catch (DatabaseLayerException ex) {
                    throw new RuntimeException(ex);
                }

                if (successResult) {
                    Notification.show("Profil erfolgreich aktualisiert", 3000, Notification.Position.MIDDLE);
                    UI.getCurrent().navigate(Globals.Pages.SHOW_JOBPOSTINGS);
                } else {
                    Notification.show("Bei der Aktualisierung des Profils ist ein Fehler aufgetreten", 3000, Notification.Position.MIDDLE);
                }
            }
        });

        //Wenn backButton gedrückt wird
        backButton.addClickListener(e -> {
            if (currentUser.getAccountType() == AccountType.STUDENT) {
                UI.getCurrent().navigate(Globals.Pages.SHOW_JOBPOSTINGS);
            } else {
                UI.getCurrent().navigate(Globals.Pages.SHOW_APPLICATIONS);
            }
        });

        //Wenn deleteButton gedrückt wird
        deleteButton.addClickListener(e -> {
            userControl.deleteUserProfile(currentUser.getId());
            logoutUser();
        });

    }
    private void logoutUser() {
        UI ui = this.getUI().get();
        ui.getSession().close();
        ui.getPage().setLocation("/");
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        changeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(changeButton);
        backButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(backButton);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(deleteButton);
        changeButton.addClassName("change-button");
        backButton.addClassName("back-button");
        deleteButton.addClassName("delete-button");
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
        changeFachsemester.setVisible(false);
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
        changeFachsemester.setVisible(true);
    }

    /*
    private void clearForm() { //was macht die Methode?
        binder.setBean(new UserDTOImpl());
    }
    */

    boolean checkIfFormComplete(){
        boolean formComplete = true;
        if (accountType == AccountType.STUDENT) {
            String form_firstname = changeFirstname.getValue();
            String form_lastname = changeLastname.getValue();
            LocalDate form_birthday = changeBirthday.getValue();
            String form_fachsemesterStr = changeFachsemester.getValue();

            // Überprüfen, ob form_fachsemesterStr numerisch ist
            if (form_fachsemesterStr != null) {
                if (form_fachsemesterStr.isEmpty() || !isNumeric(form_fachsemesterStr) || (Integer.parseInt(form_fachsemesterStr) <= 0)) {
                    formComplete = false;
                    Notification.show("Bitte geben Sie eine gültige Zahl für das Fachsemester ein.");
                }
            } else { //Fall, dass Fachsemester null ist
                changeFachsemester.setValue(String.valueOf(currentStudent.getFachsemester()));
            }

            if (form_firstname != null) {
                if (form_firstname.length() > 64) {
                    formComplete = false;
                    Notification.show("Ihr Vorname darf nicht länger als 64 Zeichen sein.");
                }
            } else { //Fall, dass Firstname null ist
                changeFirstname.setValue(currentStudent.getFirstname());
            }

            if (form_lastname != null) {
                if (form_firstname.length() > 64) {
                    formComplete = false;
                    Notification.show("Ihr Nachname darf nicht länger als 64 Zeichen sein.");
                }
            } else { //Fall, dass Lastname null ist
                changeLastname.setValue(currentStudent.getLastname());
            }

            // Überprüfen, ob das Geburtsdatum mindestens 16 Jahre zurückliegt
            if (form_birthday != null) {
                if(!isAtLeast16YearsOld(form_birthday)) {
                    formComplete = false;
                    Notification.show("Sie müssen mindestens 16 Jahre alt sein.");
                }
            } else { //Fall, dass Birthday null ist
                changeBirthday.setValue(currentStudent.getBirthday());
            }
        }
        if (accountType == AccountType.UNTERNEHMEN) {
            String form_companyName = changeCompanyName.getValue();
            LocalDate form_foundingDate = changeFoundingDate.getValue();
            String form_employees = changeEmployees.getValue();
            String form_locations = changeLocations.getValue();
            String form_description = changeDescription.getValue();


            if (form_companyName != null) {
                if (form_companyName.length() > 64) {
                    formComplete = false;
                    Notification.show("Ihr Firmenname darf nicht länger als 64 Zeichen sein.");
                }
            } else { //Fall, dass CompanyName null ist
                changeCompanyName.setValue(currentCompany.getCompanyName());
            }

            if (form_foundingDate == null) {
                changeFoundingDate.setValue(currentCompany.getFoundingDate());
            }

            if (form_employees != null) {
                if (Integer.parseInt(form_employees) <= 0) {
                    formComplete = false;
                    Notification.show("Die Mitarbeiteranzahl muss größer als 0 sein.");
                }
            } else { //Fall, dass Employees null ist
                changeEmployees.setValue(String.valueOf(currentCompany.getEmployees()));
            }

            if (form_locations == null) {
                changeLocations.setValue(currentCompany.getLocations());
            }

            if (form_description == null) {
                changeDescription.setValue(currentCompany.getDescription());
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

}