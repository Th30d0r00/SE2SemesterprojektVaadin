package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.hellocar.control.JobPostingControl;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;

@Route(value = Globals.Pages.EDIT_JOBPOSTING, layout = AppView.class)
@PageTitle("Stellenanzeige bearbeiten")
@CssImport("./styles/views/editjobpostingview/enter-job-posting.css")
public class EditJobPostingView extends Div implements HasUrlParameter<Integer> {
    private JobPostingControl jobPostingControl;
    private AnzeigeDTO currentJobPosting;
    private TextField changeTitle;
    private TextField changeLocation;
    private ComboBox<String> changeEmploymentType;
    private TextArea changeJobDescription;

    // Erstelle die Buttons
    private Button submitButton;
    private Button cancelButton;
    private Button deleteButton;

    public EditJobPostingView(JobPostingControl jobPostingControl) {
        addClassName("editjobpostingview");
        this.jobPostingControl = jobPostingControl;

        // Erstelle die Textfelder
        changeTitle = new TextField("Titel der Anzeige");
        changeLocation = new TextField("Standort");

        // Erstelle die ComboBox für Art der Anstellung
        changeEmploymentType = new ComboBox<>("Art der Anstellung");
        changeEmploymentType.setItems("Vollzeit", "Teilzeit", "Praktikum", "Freelancer");

        // Erstelle das TextArea für Stellenbeschreibung
        changeJobDescription = new TextArea("Stellenbeschreibung");
        changeJobDescription.setWidthFull();
        changeJobDescription.setHeight("200px");

        add(createFormLayout());
        add(createButtonLayout());

        submitButton.addClickListener(event -> {
            try {
                handleUpdateJobPosting();
            } catch (DatabaseLayerException e) {
                Notification.show("Fehler beim Aktualisieren der Stellenanzeige", 3000, Notification.Position.MIDDLE);
            }
        });
        cancelButton.addClickListener(event -> UI.getCurrent().navigate(Globals.Pages.SHOW_APPLICATIONS));
        deleteButton.addClickListener(event -> handleDeleteJobPosting());
    }

    private void handleUpdateJobPosting() throws DatabaseLayerException {
        if (changeTitle.getValue() == null) {
            changeTitle.setValue(currentJobPosting.getJobTitle());
        }
        if (changeLocation.getValue() == null) {
            changeLocation.setValue(currentJobPosting.getStandort());
        }
        if (changeEmploymentType.getValue() == null) {
            changeEmploymentType.setValue(currentJobPosting.getJobType());
        }
        if (changeJobDescription.getValue() == null) {
            changeJobDescription.setValue(currentJobPosting.getJobDescription());
        }

        boolean successResult = jobPostingControl.updateJobPosting(currentJobPosting.getId(), changeTitle.getValue(),
                changeLocation.getValue(), changeEmploymentType.getValue(), changeJobDescription.getValue());

        if (successResult) {
            Notification.show("Stellenanzeige erfolgreich aktualisiert", 3000, Notification.Position.MIDDLE);
            UI.getCurrent().navigate(Globals.Pages.SHOW_APPLICATIONS);
        } else {
            Notification.show("Bei der Aktualisierung der Stellenanzeige ist ein Fehler aufgetreten", 3000, Notification.Position.MIDDLE);
        }
    }

    private void handleDeleteJobPosting() {
        try {
            jobPostingControl.deleteJobPosting(currentJobPosting.getId());
            Notification.show("Stellenanzeige erfolgreich gelöscht", 3000, Notification.Position.MIDDLE);
            UI.getCurrent().navigate(Globals.Pages.SHOW_APPLICATIONS);
        } catch (DatabaseLayerException e) {
            Notification.show("Beim Löschen der Stellenanzeige ist ein Fehler aufgetreten", 3000, Notification.Position.MIDDLE);
        }
    }

    private Component createButtonLayout() {
        submitButton = new Button("Änderungen Speichern");
        cancelButton = new Button("Abbrechen");
        deleteButton = new Button("Anzeige Löschen");
        submitButton.addClassName("submit-button");
        cancelButton.addClassName("back-button");
        deleteButton.addClassName("delete-button");
        HorizontalLayout buttonLayout = new HorizontalLayout(submitButton, cancelButton, deleteButton);
        buttonLayout.setSpacing(true);
        return buttonLayout;
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(changeTitle, changeLocation, changeEmploymentType, changeJobDescription);
        return formLayout;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Integer integer) {
        try {
            int jobId = integer;
            currentJobPosting = jobPostingControl.findJobPosting(jobId);
            if (currentJobPosting != null) {
                changeTitle.setValue(currentJobPosting.getJobTitle());
                changeLocation.setValue(currentJobPosting.getStandort());
                changeEmploymentType.setValue(currentJobPosting.getJobType());
                changeJobDescription.setValue(currentJobPosting.getJobDescription());
            } else {
                Notification.show("Stellenanzeige nicht gefunden", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().navigate(Globals.Pages.SHOW_APPLICATIONS);
            }
        } catch (NumberFormatException e) {
            Notification.show("Ungültige Stellenanzeigen-ID", 3000, Notification.Position.MIDDLE);
            UI.getCurrent().navigate(Globals.Pages.SHOW_APPLICATIONS);
        } catch (DatabaseLayerException e) {
            throw new RuntimeException(e);
        }
    }
}
