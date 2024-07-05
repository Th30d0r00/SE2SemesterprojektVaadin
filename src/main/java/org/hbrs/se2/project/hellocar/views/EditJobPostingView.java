package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.hellocar.control.EditJobPostingControl;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.AnzeigeDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;

@Route(value = Globals.Pages.EDIT_JOBPOSTING, layout = AppView.class)
@PageTitle("Stellenanzeige bearbeiten")
public class EditJobPostingView extends Div implements HasUrlParameter<AnzeigeDTOImpl> {
    EditJobPostingControl editJobPostingControl;
    AnzeigeDTO currentJobPosting;

    TextField changeTitle;
    TextField changeLocation;
    ComboBox<String> changeEmploymentType;
    TextArea changeJobDescription;

    // Erstelle die Buttons
    Button submitButton = new Button("Änderungen speichern");
    Button cancelButton = new Button("Abbrechen");
    Button deleteButton = new Button("Stellenanzeige löschen");

    public EditJobPostingView(EditJobPostingControl editJobPostingControl) {
        addClassName("editjobpostingview");
        this.editJobPostingControl = editJobPostingControl;
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

        submitButton.addClickListener(event -> {

            if (changeTitle.getValue() == null) {
                changeTitle.setValue(currentJobPosting.getJobTitle());
            } if (changeLocation.getValue() == null) {
                changeLocation.setValue(currentJobPosting.getStandort());
            } if (changeEmploymentType.getValue() == null) {
                changeEmploymentType.setValue(currentJobPosting.getJobType());
            } if (changeJobDescription.getValue() == null) {
                changeJobDescription.setValue(currentJobPosting.getJobDescription());
            }

            boolean successResult = editJobPostingControl.updateJobPosting(currentJobPosting.getId(), changeTitle.getValue(),
                    changeLocation.getValue(), changeEmploymentType.getValue(), changeJobDescription.getValue());

            if (successResult) {
                Notification.show("Stellenanzeige erfolgreich aktualisiert", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().navigate(Globals.Pages.SHOW_APPLICATIONS);
            } else {
                Notification.show("Bei der Aktualisierung der Stellenanzeige ist ein Fehler aufgetreten", 3000, Notification.Position.MIDDLE);
            }
        });
        cancelButton.addClickListener(event -> {
            UI.getCurrent().navigate(Globals.Pages.SHOW_APPLICATIONS);
        });
        deleteButton.addClickListener(event -> {
            try {
                editJobPostingControl.deleteJobPosting(currentJobPosting.getId());
            } catch (DatabaseLayerException e) {
                Notification.show("Beim Löschen der Stellenanzeige ist ein Fehler aufgetreten", 3000, Notification.Position.MIDDLE);
            }
        });
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter AnzeigeDTOImpl anzeigeDTO) {
        currentJobPosting = anzeigeDTO;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(submitButton);
        cancelButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(cancelButton);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(deleteButton);
        submitButton.addClassName("submit-button");
        cancelButton.addClassName("cancel-button");
        deleteButton.addClassName("delete-button");
        return buttonLayout;
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(changeTitle, changeLocation, changeEmploymentType, changeJobDescription);
        return formLayout;
    }

    private Component createTitle() {
        return new H3("Stellenanzeige bearbeiten");
    }
}
