package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.textfield.PasswordField;
import org.hbrs.se2.demo.registration.RegistrationResult;
import org.hbrs.se2.project.hellocar.control.ManageCarControl;
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
import org.hbrs.se2.project.hellocar.util.Security;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Route(value = "registration" )
@PageTitle("User Registration")
@CssImport("./styles/views/entercar/enter-car-view.css")
public class RegistrationView extends Div {  // 3. Form (Spezialisierung / Vererbung)

    //Formfelder & Registrierungsbutton
    private ComboBox<AccountType> accountType = new ComboBox<>("Firma/Student");
    private TextField email = new TextField("E-Mail");
    private PasswordField password = new PasswordField("Passwort");
    private TextField userId = new TextField("Nutzername");

    private TextField companyName = new TextField("Firmenname");
    private DatePicker foundingDate = new DatePicker("Gründungsdatum");
    private TextField employees = new TextField("Anzahl Mitarbeiter");

    private TextField firstname = new TextField("Vorname");
    private TextField lastname = new TextField("Nachname");
    private DatePicker birthday = new DatePicker("Geburtsdatum");

    private Button register = new Button("Register");

    private Binder<UserDTOImpl> binder = new Binder(UserDTOImpl.class);

    //RegistrationView
    public RegistrationView( ManageCarControl carService) {
        //ToDo: RegistrationControl; UserDTO, Binding; ExceptionHandling;

        addClassName("enter-car-view");
        accountType.setItems(AccountType.values());

        DefaultVisibility();

        accountType.addValueChangeListener(e -> {
            if(e.getValue() == AccountType.STUDENT){
                accountType.setVisible(true);
                StudentVisibility();
            }
            if(e.getValue() == AccountType.UNTERNEHMEN){
                accountType.setVisible(true);
                CompanyVisibility();
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

            boolean formComplete = CheckIfFormComplete();

            if(formComplete){
                RegistrationControl regControl = new RegistrationControl();
                UserDTO userDTO = new UserDTOImpl();

                try{
                    if(accountType.getValue() == AccountType.STUDENT){
                        FillUserDtoAsStudent(userDTO);
                    }
                    if(accountType.getValue() == AccountType.UNTERNEHMEN){
                        FillUserDtoAsCompany(userDTO);
                    }
                } catch (NumberFormatException nfe){
                    Notification.show("Irgendwo fehlerhafte Eingabe");
                }

                RegistrationResult result = regControl.registerUser(userDTO);
                Notification.show(result.getMessage());
                clearForm();

                //UI.getCurrent().navigate( Globals.Pages.LOGIN_VIEW );
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
        formLayout.add(email, userId, password, accountType, companyName,
                foundingDate, employees, firstname, lastname, birthday);
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(register);
        return buttonLayout;
    }

    private void DefaultVisibility(){
        accountType.setVisible(true);
        password.setVisible(true);
        email.setVisible(true);
        userId.setVisible(true);

        companyName.setVisible(false);
        foundingDate.setVisible(false);
        employees.setVisible(false);
        firstname.setVisible(false);
        lastname.setVisible(false);
        birthday.setVisible(false);
    }

    private void StudentVisibility(){
        companyName.setVisible(false);
        foundingDate.setVisible(false);
        employees.setVisible(false);
        firstname.setVisible(true);
        lastname.setVisible(true);
        birthday.setVisible(true);
    }

    private void CompanyVisibility(){
        companyName.setVisible(true);
        foundingDate.setVisible(true);
        employees.setVisible(true);
        firstname.setVisible(false);
        lastname.setVisible(false);
        birthday.setVisible(false);
    }

    private void FillUserDtoAsStudent(UserDTO userDTO){
        userDTO.setSalt(Security.getSalt());
        userDTO.setHashValue(Security.getHash(password.getValue(), userDTO.getSalt()));
        userDTO.setEmail(email.getValue());
        //userDTO.setPassword(password.getValue());
        userDTO.setUserId(userId.getValue());
        userDTO.setAccountType(accountType.getValue());

        StudentDTO studentDTO = new StudentDTOImpl();
        studentDTO.setFirstname(firstname.getValue());
        studentDTO.setLastname(lastname.getValue());
        studentDTO.setBirthday(birthday.getValue());

        userDTO.setStudent(studentDTO);
    }

    private void FillUserDtoAsCompany(UserDTO userDTO){
        userDTO.setSalt(Security.getSalt());
        userDTO.setHashValue(Security.getHash(password.getValue(), userDTO.getSalt()));
        userDTO.setEmail(email.getValue());
        //userDTO.setPassword(password.getValue());
        userDTO.setUserId(userId.getValue());
        userDTO.setAccountType(accountType.getValue());

        CompanyDTO companyDTO = new CompanyDTOImpl();
        companyDTO.setCompanyName(companyName.getValue());
        companyDTO.setFoundingDate(foundingDate.getValue());
        companyDTO.setEmployees( Integer.parseInt(employees.getValue()));

        userDTO.setCompany(companyDTO);
    }

    private boolean CheckIfFormComplete(){
        boolean formComplete = true;

        AccountType form_type = accountType.getValue();
        String form_mail = email.getValue();
        String form_pw = password.getValue();
        String form_userId = userId.getValue();

        if(form_type == null){
            formComplete = false;
            Notification.show("Bitte wählen Sie ein Kundenkonto aus (Student/Unternehmen).");
        }
        if(form_mail == null){
            formComplete = false;
            Notification.show("Bitte geben Sie eine E-Mail Adresse an.");
        }
        if(!ValidEmail(form_mail)){
            formComplete = false;
            Notification.show("Bitte geben Sie eine gültige E-Mail Adresse an.");
        }
        if (form_pw == null) {
            formComplete = false;
            Notification.show("Bitte geben Sie ein Passwort ein.");
        }
        if(form_pw != null && form_pw.length() < 8){
            formComplete = false;
            Notification.show("Ihr Passwort muss mindestens 8 Zeichen lang sein.");
        }
        if(form_userId == null){
            formComplete = false;
            Notification.show("Bitte geben Sie einen Nutzernamen an.");
        }
        if(form_userId != null && (form_userId.length() < 8 || form_userId.length() > 24)){
            formComplete = false;
            Notification.show("Ihr Nutzername muss zwischen 8 und 24 Zeichen lang sein.");
        }

        if(form_type == AccountType.STUDENT){
            String form_firstname = firstname.getValue();
            String form_lastname = lastname.getValue();
            LocalDate form_birthday = birthday.getValue();

            if(form_firstname == null){
                formComplete = false;
                Notification.show("Bitte geben Sie einen Vornamen ein.");
            }

            if(form_firstname != null && form_firstname.length() > 64){
                formComplete = false;
                Notification.show("Ihr Vorname darf nicht länger als 64 Zeichen sein.");
            }

            if(form_lastname == null){
                formComplete = false;
                Notification.show("Bitte geben Sie einen Nachnamen ein.");
            }

            if(form_lastname != null && form_lastname.length() > 64){
                formComplete = false;
                Notification.show("Ihr Nachname darf nicht länger als 64 Zeichen sein.");
            }

            //Birthdate muss mindestens 16 Jahre zurückliegen
        }

        if(form_type == AccountType.UNTERNEHMEN){
            String form_companyName = companyName.getValue();
            LocalDate form_foundingDate = foundingDate.getValue();

            if(form_companyName == null){
                formComplete = false;
                Notification.show("Bitte geben Sie einen Firmennamen an.");
            }

            if(form_companyName != null && form_companyName.length() > 64){
                formComplete = false;
                Notification.show("Ihr Firmenname darf nicht länger als 64 Zeichen sein.");
            }
        }

        return formComplete;
    }

    //100% not stolen from Stackoverflow
    public boolean ValidEmail(String emailStr) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

}
