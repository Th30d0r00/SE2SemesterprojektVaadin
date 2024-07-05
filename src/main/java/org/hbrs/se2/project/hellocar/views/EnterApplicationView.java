package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.hellocar.control.*;
import org.hbrs.se2.project.hellocar.dao.AnzeigeDAO;
import org.hbrs.se2.project.hellocar.dao.CompanyDAO;
import org.hbrs.se2.project.hellocar.dao.StudentDAO;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.ApplicationDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.SQLException;
import java.util.Arrays;

@Route(value = Globals.Pages.JOB_APPLY, layout = AppView.class)
@PageTitle("Neue Bewerbung erstellen")
public class EnterApplicationView extends VerticalLayout implements HasUrlParameter<Integer> {

    private TextField telephoneField;
    private TextField currentEmploymentField;
    private DatePicker availabilityField;
    private TextField locationField;
    private TextArea motivationField;
    private TextArea resumeField;
    private ApplicationControl applicationControl;
    private ApplicationDTO applicationDTO = new ApplicationDTOImpl();
    private Button cancelButton = new Button("Abbrechen");
    private Button applyButton = new Button("Bewerbung abschicken");
    private Integer jobId;
    private Integer companyId;

    public EnterApplicationView() {
        // Implementierung der View
        addClassName("enter-application-view");
        add(createFormLayout());
        add(createButtonLayout());
        applicationControl = new ApplicationControl();

        // Aktionen für die Buttons
        applyButton.addClickListener(event -> {
            // Validierungslogik
            boolean formComplete = true;

            if (telephoneField.isEmpty()) {
                Notification.show("Bitte geben Sie Ihre Telefonnummer ein");
                formComplete = false;
            }

            if (currentEmploymentField.isEmpty()) {
                Notification.show("Bitte geben Sie Ihre aktuelle Anstellung ein");
                formComplete = false;
            }

            if (availabilityField.isEmpty()) {
                Notification.show("Bitte geben Sie Ihre Verfügbarkeit ein");
                formComplete = false;
            }

            if (locationField.isEmpty()) {
                Notification.show("Bitte geben Sie Ihren Wohnort ein");
                formComplete = false;
            }

            if (motivationField.isEmpty()) {
                Notification.show("Bitte geben Sie Ihre Motivation ein");
                formComplete = false;
            }

            if (resumeField.isEmpty()) {
                Notification.show("Bitte geben Sie Ihren Lebenslauf ein");
                formComplete = false;
            }

            if (formComplete) {
                applicationDTO.setTelefonnummer(telephoneField.getValue());
                applicationDTO.setBeschaeftigung(currentEmploymentField.getValue());
                applicationDTO.setVerfuegbar(availabilityField.getValue());
                applicationDTO.setWohnort(locationField.getValue());
                applicationDTO.setMotivationsschreiben(motivationField.getValue());
                applicationDTO.setLebenslauf(resumeField.getValue());
                applicationDTO.setAppliedAt(java.time.LocalDateTime.now());
                applicationDTO.setStatus("verschickt");

                try {
                    applicationControl.saveApplication(applicationDTO);
                } catch (DatabaseLayerException e) {
                    throw new RuntimeException(e);
                }
                UI.getCurrent().navigate(Globals.Pages.SHOW_JOBPOSTINGS);
            }
        });

        cancelButton.addClickListener(event -> {
            // Logik für den Abbrechen-Button
            UI.getCurrent().navigate(Globals.Pages.SHOW_JOBPOSTINGS);
        });
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();

        telephoneField = new TextField();
        telephoneField.setLabel("Telefonnummer");

        currentEmploymentField = new TextField();
        currentEmploymentField.setLabel("Aktuelle Anstellung");

        availabilityField = new DatePicker();
        availabilityField.setLabel("Verfügbarkeit");

        locationField = new TextField();
        locationField.setLabel("Wohnort");

        motivationField = new TextArea();
        motivationField.setLabel("Motivation");
        motivationField.setWidthFull();

        resumeField = new TextArea();
        resumeField.setLabel("Lebenslauf");
        resumeField.setWidthFull();

        formLayout.add(telephoneField, currentEmploymentField, availabilityField, locationField, motivationField, resumeField);

        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout(cancelButton, applyButton);
        buttonLayout.setSpacing(true);
        applyButton.addClassName("apply-button");
        cancelButton.addClassName("cancel-button");
        return buttonLayout;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Integer parameter) {
        if (parameter != null) {
            jobId = parameter;
            JobPostingControl JobPostingControl = new JobPostingControl();
            StudentControl studentControl= new StudentControl();
            UserControl userControl = new UserControl();
            try {
                AnzeigeDTO anzeigeDTO = JobPostingControl.findJobPosting(jobId);
                //Check ob anzeigeDTO null ist, wenn ja dann handelt es sich um eine Initiative Bewerbung. In dem Fall
                //wird der Parameter als companyId interpretiert
                if (anzeigeDTO == null) {
                    companyId = parameter;
                    CompanyControl companyControl = new CompanyControl();
                    applicationDTO.setCompany(companyControl.findCompany(companyId));
                    applicationDTO.setStudent(studentControl.findStudent(getCurrentUser().getId()));
                    applicationDTO.setUser(userControl.findUser(getCurrentUser().getId()));
                } else {
                    applicationDTO.setCompany(anzeigeDTO.getCompany());
                    applicationDTO.setStellenanzeige(anzeigeDTO);
                    applicationDTO.setStudent(studentControl.findStudent(getCurrentUser().getId()));
                    applicationDTO.setUser(userControl.findUser(getCurrentUser().getId()));
                }
            } catch (DatabaseLayerException e) {
                e.printStackTrace();
                Notification.show("Fehler beim Abrufen der Application");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }
}
