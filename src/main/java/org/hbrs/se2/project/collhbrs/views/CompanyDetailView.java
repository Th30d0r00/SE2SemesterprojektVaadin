package org.hbrs.se2.project.collhbrs.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.collhbrs.dao.CompanyDAO;
import org.hbrs.se2.project.collhbrs.dao.UserDAO;
import org.hbrs.se2.project.collhbrs.dtos.CompanyDTO;
import org.hbrs.se2.project.collhbrs.dtos.UserDTO;
import org.hbrs.se2.project.collhbrs.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.collhbrs.util.Globals;

import java.sql.SQLException;

/*
 * zeigt dem Bewerber (Student) die Details eines Unternehmens an
 * */

@Route(value = Globals.Pages.COMPANY_DETAILS, layout = AppView.class)
@PageTitle("Unternehmensdetails")
@CssImport("./styles/views/companydetailview/company-detail-view.css")
public class CompanyDetailView extends VerticalLayout implements HasUrlParameter<Integer> {
    private final UserDAO userDAO;
    private final CompanyDAO companyDAO;
    private TextField emailField;
    private TextField nameField;
    private TextField employeesField;
    private TextField foundingdateField;
    private TextField locationsField;
    private TextField descriptionField;
    private Integer companyId;
    private Button backButton;
    private Button applyButton;

    public CompanyDetailView() {
        this.userDAO = new UserDAO();
        this.companyDAO = new CompanyDAO();

        add(createFormLayout());
        add(createButtonLayout());

        //Aktionen für die Buttons
        applyButton.addClickListener(event -> UI.getCurrent().navigate(Globals.Pages.JOB_APPLY + "/" + companyId));
        backButton.addClickListener(event -> UI.getCurrent().navigate(Globals.Pages.SHOW_COMPANIES));
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        nameField = new TextField();
        nameField.setLabel("Name");

        emailField = new TextField();
        emailField.setLabel("E-Mail");

        employeesField = new TextField();
        employeesField.setLabel("Anzahl der Mitarbeiter");

        foundingdateField = new TextField();
        foundingdateField.setLabel("Gründungsdatum");

        locationsField = new TextField();
        locationsField.setLabel("Standorte");

        descriptionField = new TextField();
        descriptionField.setLabel("Kurze Beschreibung des Unternehmens");

        formLayout.addFormItem(nameField, "Name");
        formLayout.addFormItem(emailField, "E-Mail");
        formLayout.addFormItem(employeesField, "Anzahl der Mitarbeiter");
        formLayout.addFormItem(foundingdateField, "Gründungsdatum");
        formLayout.addFormItem(locationsField, "Standorte");
        formLayout.addFormItem(descriptionField, "Kurze Beschreibung des Unternehmens");

        return formLayout;
    }

    private Component createButtonLayout() {
        backButton = new Button("Zurück");
        applyButton = new Button("Initiativbewerbung");
        backButton.addClassName("back-button");
        applyButton.addClassName("apply-button");
        HorizontalLayout buttonLayout = new HorizontalLayout(backButton, applyButton);
        buttonLayout.setSpacing(true);

        return buttonLayout;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Integer companyId) {
        // Setzen der companyId und Überprüfen, ob die companyId null ist
        this.companyId = companyId;
        if (companyId == null) {
            System.out.println("Null Value is not supported");
            return;
        }
        try {
            // Abrufen der Company- und User-Daten anhand der companyId
            CompanyDTO companyDTO = companyDAO.getCompanyById(companyId);
            UserDTO userDTO = userDAO.findUserById(companyId);

            // Überprüfen, ob beide DTOs erfolgreich abgerufen wurden
            if (userDTO != null && companyDTO != null) {
                // Setzen der Werte in die entsprechenden Felder
                nameField.setValue(companyDTO.getCompanyName());
                emailField.setValue(userDTO.getEmail());
                employeesField.setValue(Integer.toString(companyDTO.getEmployees()));
                foundingdateField.setValue(String.valueOf(companyDTO.getFoundingDate()));
                locationsField.setValue(companyDTO.getLocations());
                descriptionField.setValue(companyDTO.getDescription());
            } else {
                // Fehlerbehandlung, wenn eines der DTOs null ist
                Notification.show("CompanyDTO oder UserDTO ist null: ", 3000, Notification.Position.MIDDLE);
            }
        } catch (SQLException e) {
            // Fehlerbehandlung bei SQL-Fehlern
            Notification.show("SQLException: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        } catch (DatabaseLayerException e) {
            // Fehlerbehandlung bei Problemen mit der Datenbank
            Notification.show("DatabaseLayerException: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }

}
