package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.hellocar.dao.CompanyDAO;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;

import java.sql.SQLException;

@Route(value = "companydetailview", layout = AppView.class)
//@Route(value = Globals.Pages.COMPANY_DETAILS, layout = AppView.class)
@PageTitle(value = "Anzeigendetails")
public class CompanyDetailView extends VerticalLayout implements HasUrlParameter<Integer> {
    private final UserDAO userDAO;
    private final CompanyDAO companyDAO;
    private TextField emailField;
    private TextField nameField;
    private TextField employeesField;
    private TextField foundingdateField;
    private TextField locationsField;
    private TextField descriptionField;

    public CompanyDetailView() {
        this.userDAO = new UserDAO();
        this.companyDAO = new CompanyDAO();

        // FormLayout zur Anordnung der Textboxen und Labels
        FormLayout formLayout = new FormLayout();

        // Erstellen der Textfelder und Labels
        nameField = new TextField();
        nameField.setLabel("Name");

        emailField = new TextField();
        emailField.setLabel("E-Mail");

        employeesField = new TextField();
        employeesField.setLabel("Anzahl der Mitarbeiter");

        foundingdateField = new TextField();
        foundingdateField.setLabel("Gründungssatum");

        locationsField = new TextField();
        locationsField.setLabel("Standorte");

        descriptionField = new TextField();
        descriptionField.setLabel("Kurze Beschreibung des Unternehmens");

        // Fügen Sie die Textfelder zum Layout hinzu
        formLayout.addFormItem(nameField, "Name");
        formLayout.addFormItem(emailField, "E-Mail");
        formLayout.addFormItem(employeesField, "Anzahl der Mitarbeiter");
        formLayout.addFormItem(foundingdateField, "Gründungsdatum");
        formLayout.addFormItem(locationsField, "Standorte");
        formLayout.addFormItem(descriptionField, "Kurze Beschreibung des Unternehmens");

        // Buttons "Zurück" und "Bewerben"
        Button backButton = new Button("Zurück", event -> {
            // Logik für den Zurück-Button
            //getUI().ifPresent(ui -> ui.navigate(""));
            UI.getCurrent().navigate( Globals.Pages.SHOW_COMPANIES);
        });

        Button applyButton = new Button("Initiativbewerbung", event -> {
            // Logik für den Bewerben-Button

        });

        // Layout für die Buttons
        HorizontalLayout buttonLayout = new HorizontalLayout(backButton, applyButton);

        // Fügen Sie das Formular und die Buttons zum Hauptlayout hinzu
        add(formLayout, buttonLayout);
    }
    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Integer companyId) {
        if(companyId == null) {
            System.out.println("Null Value is not supported");
        }
        CompanyDTO companyDTO;
        UserDTO userDTO;

        try {
            companyDTO = companyDAO.getCompanyById(companyId.intValue());
            userDTO = userDAO.findUserById(companyId.intValue());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DatabaseLayerException e) {
            throw new RuntimeException(e);
        }
        if (userDTO != null && companyDTO != null) {
            nameField.setValue(companyDTO.getCompanyName());
            emailField.setValue(userDTO.getEmail());
            employeesField.setValue(Integer.toString(companyDTO.getEmployees()));
            foundingdateField.setValue(String.valueOf(companyDTO.getFoundingDate()));
            locationsField.setValue(companyDTO.getLocations());
            descriptionField.setValue(companyDTO.getDescription());
        }

    }
}
