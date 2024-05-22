package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.*;
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

@Route(value = "registration" )
@PageTitle("User Registration")
@CssImport("./styles/views/entercar/enter-car-view.css")
public class RegistrationView extends Div {  // 3. Form (Spezialisierung / Vererbung)

    //Formfelder & Registrierungsbutton
    private ComboBox<AccountType> accountType = new ComboBox<>("Firma/Student");
    private TextField email = new TextField("E-Mail");
    private TextField password = new TextField("Passwort");
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

                if(accountType.getValue() == AccountType.STUDENT){
                    FillUserDtoAsStudent(userDTO);
                }
                if(accountType.getValue() == AccountType.UNTERNEHMEN){
                    FillUserDtoAsCompany(userDTO);
                }

                RegistrationResult result = regControl.registerUser(userDTO);

                Notification.show(result.getMessage());
                clearForm();

                //UI.getCurrent().navigate( Globals.Pages.LOGIN_VIEW );
            }
            else{
                Notification.show("Junge, füll die Form halt korrekt aus.");
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
        formLayout.add(email, password, accountType, companyName, foundingDate, employees, firstname, lastname, birthday);
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
        userDTO.setPassword(password.getValue());
        userDTO.setUserId(userId.getValue());

        StudentDTO studentDTO = new StudentDTOImpl();
        studentDTO.setFirstname(firstname.getValue());
        studentDTO.setLastname(lastname.getValue());
        studentDTO.setBirthday(birthday.getValue());

        userDTO.setStudent(studentDTO);
    }

    private void FillUserDtoAsCompany(UserDTO userDTO){
        userDTO.setEmail(email.getValue());
        userDTO.setPassword(password.getValue());
        userDTO.setUserId(userId.getValue());

        CompanyDTO companyDTO = new CompanyDTOImpl();
        companyDTO.setCompanyName(companyName.getValue());
        companyDTO.setFoundingDate(foundingDate.getValue());
        companyDTO.setEmployees( Integer.parseInt(employees.getValue()));
    }

    private boolean CheckIfFormComplete(){
        return true;
    }
}
