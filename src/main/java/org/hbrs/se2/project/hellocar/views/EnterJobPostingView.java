package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.hellocar.control.CompanyControl;
import org.hbrs.se2.project.hellocar.control.JobPostingControl;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.AnzeigeDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;

import java.sql.SQLException;

@Route(value = Globals.Pages.ENTER_JOBPOSTING, layout = AppView.class)
@PageTitle("Stellenanzeige erstellen")
@CssImport("./styles/views/enterjobposting/enter-job-posting-view.css")
@Uses(TextArea.class)
public class EnterJobPostingView extends VerticalLayout {

    public EnterJobPostingView() { //Control übergeben?

        // Erstelle die Textfelder
        TextField titleField = new TextField("Titel der Anzeige");
        TextField locationField = new TextField("Standort");

        // Erstelle die ComboBox für Art der Anstellung
        ComboBox<String> employmentTypeField = new ComboBox<>("Art der Anstellung");
        employmentTypeField.setItems("Vollzeit", "Teilzeit", "Praktikum", "Freelancer");

        // Erstelle das TextArea für Stellenbeschreibung
        TextArea jobDescriptionField = new TextArea("Stellenbeschreibung");
        jobDescriptionField.setWidthFull();
        jobDescriptionField.setHeight("200px");

        // Erstelle die Buttons
        Button submitButton = new Button("Stellenanzeige veröffentlichen");
        Button cancelButton = new Button("Abbrechen");

        submitButton.addClassName("submit-button");
        cancelButton.addClassName("cancel-button");

        // Füge Aktionen für die Buttons hinzu
        submitButton.addClickListener(event -> {
            // Validierungslogik
            boolean formComplete = true;

            if (titleField.isEmpty()) {
                formComplete = false;
                Notification.show("Bitte geben Sie einen Titel für die Anzeige ein.");
            }
            if (locationField.isEmpty()) {
                formComplete = false;
                Notification.show("Bitte geben Sie einen Standort an.");
            }
            if (employmentTypeField.isEmpty()) {
                formComplete = false;
                Notification.show("Bitte wählen Sie eine Art der Anstellung aus.");
            }
            if (jobDescriptionField.isEmpty()) {
                formComplete = false;
                Notification.show("Bitte geben Sie eine Stellenbeschreibung ein.");
            }

            if (formComplete) {
                // Aktionen beim Klicken auf den Button (z.B. Validierung, Speichern, etc.)
                AnzeigeDTO anzeigeDTO = new AnzeigeDTOImpl();
                anzeigeDTO.setJobTitle(titleField.getValue());
                anzeigeDTO.setStandort(locationField.getValue());
                anzeigeDTO.setJobType(employmentTypeField.getValue());
                anzeigeDTO.setJobDescription(jobDescriptionField.getValue());
                anzeigeDTO.setPublicationDate(java.time.LocalDateTime.now());
                System.out.println(getCurrentUser().getId());

                JobPostingControl jobPostingControl = new JobPostingControl();
                CompanyControl companyControl = new CompanyControl();
                try {
                    anzeigeDTO.setCompany(companyControl.findCompany(getCurrentUser().getId()));
                } catch (DatabaseLayerException | SQLException e) {
                    throw new RuntimeException(e);
                }

                try {
                    jobPostingControl.saveJobPosting(anzeigeDTO);
                    Notification.show("Stellenanzeige erfolgreich veröffentlicht", 3000, Notification.Position.MIDDLE);

                    // Leere die Felder nach erfolgreicher Eingabe
                    titleField.clear();
                    locationField.clear();
                    employmentTypeField.clear();
                    jobDescriptionField.clear();
                } catch (DatabaseLayerException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        cancelButton.addClickListener(event -> {
            // Aktion beim Abbrechen (z.B. Leeren der Felder oder Navigation zu einer anderen Seite)
            // titleField.clear();
            // locationField.clear();
            // employmentTypeField.clear();
            // jobDescriptionField.clear();
            UI.getCurrent().navigate(ShowApplicationsView.class);
        });

        // Erstelle ein horizontales Layout für die Buttons
        HorizontalLayout buttonLayout = new HorizontalLayout(submitButton, cancelButton);
        buttonLayout.setSpacing(true);

        // Erstelle ein horizontales Layout für Titel und Standort
        HorizontalLayout titleLocationLayout = new HorizontalLayout(titleField, locationField);
        titleLocationLayout.setWidthFull();
        titleLocationLayout.setSpacing(true);

        // Füge die Komponenten zum Layout hinzu
        add(titleLocationLayout, employmentTypeField, jobDescriptionField, buttonLayout);
    }

    public UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}
