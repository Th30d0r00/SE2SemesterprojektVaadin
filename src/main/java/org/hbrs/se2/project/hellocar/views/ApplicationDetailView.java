package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.open.App;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.hbrs.se2.project.hellocar.dao.ApplicationDAO;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Route(value = "applicationdetailview", layout = AppView.class)
@PageTitle(value = "Bewerbungsdetails")
@CssImport("./styles/views/applicationdetailview/application-detail-view.css")
public class ApplicationDetailView extends VerticalLayout implements HasUrlParameter<Integer> {
    private ApplicationDTO applicationDTO;
    private final ApplicationDAO applicationDAO;
    private final UserDAO userDAO;

    private final TextField jobTitle;
    private final TextField standort;
    private final TextField firstName;
    private final TextField lastName;
    private final TextField email;
    private final TextField fachsemester;
    private final TextField beschaeftigung;
    private final TextField wohnort;
    private final TextField verfuegbar;
    private final TextField motivationsschreiben;
    private final TextField lebenslauf;
    private final TextField verschicktAm;

    public ApplicationDetailView() {
        this.applicationDAO = new ApplicationDAO();
        this.userDAO = new UserDAO();

        FormLayout formLayout = new FormLayout();

        //Erstellen der Textfelder und Labels
        jobTitle = new TextField();
        jobTitle.setLabel("Job Titel");

        standort = new TextField();
        standort.setLabel("Standort");

        firstName = new TextField();
        firstName.setLabel("Vorname");

        lastName = new TextField();
        lastName.setLabel("Nachname");

        email = new TextField();
        email.setLabel("E-Mail-Adresse");

        fachsemester = new TextField();
        fachsemester.setLabel("Fachsemester");

        beschaeftigung = new TextField();
        beschaeftigung.setLabel("Art der Beschäftigung");

        wohnort = new TextField();
        wohnort.setLabel("Wohnort");

        verfuegbar = new TextField();
        verfuegbar.setLabel("Verfügbar ab ...");

        motivationsschreiben = new TextField();
        motivationsschreiben.setLabel("Motivationsschreiben");

        lebenslauf = new TextField();
        lebenslauf.setLabel("Lebenslauf");

        verschicktAm = new TextField();
        verschicktAm.setLabel("Bewerbung abgeschickt am ...");

        // Fügen Sie die Textfelder zum Layout hinzu
        formLayout.addFormItem(jobTitle, "Job Titel");
        formLayout.addFormItem(standort, "Standort");
        formLayout.addFormItem(firstName, "Vorname");
        formLayout.addFormItem(lastName, "Nachname");
        formLayout.addFormItem(email, "E-Mail-Adresse");
        formLayout.addFormItem(fachsemester, "Fachsemester");
        formLayout.addFormItem(beschaeftigung, "Art der Beschäftigung");
        formLayout.addFormItem(wohnort, "Wohnort");
        formLayout.addFormItem(verfuegbar, "Verfuegbar ab");
        formLayout.addFormItem(motivationsschreiben, "Motivationsschreiben");
        formLayout.addFormItem(lebenslauf, "Lebenslauf");
        formLayout.addFormItem(verschicktAm, "Verschickt am");

        //Buttons "annehmen", "ablehnen", "abbrechen"
        Button backButton = new Button("Zurück", event -> UI.getCurrent().navigate(Globals.Pages.SHOW_APPLICATIONS));
        Button acceptButton = new Button("Bewerbung annehmen", event -> this.applicationDTO.setStatus("angenommen"));
        Button refuseButton = new Button("Bewerbung ablehnen", event -> this.applicationDTO.setStatus("abgelehnt"));

        backButton.addClassName("back-button");
        acceptButton.addClassName("accept-button");
        refuseButton.addClassName("refuse-button");

        //Layout für die Buttons
        HorizontalLayout buttonLayout = new HorizontalLayout(backButton, acceptButton, refuseButton);

        //Formulare zum Hauptlayout hinzufügen
        add(formLayout, buttonLayout);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Integer applicationId) {
        if(applicationId == null) {
            System.out.println("Null Value is not supported");
        }
        try {
            ApplicationDTO applicationDTO = applicationDAO.getApplicationById(applicationId);
            if (applicationId != null) {
                final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MMMM uuuu", Locale.ENGLISH);

                jobTitle.setValue(applicationDTO.getStellenanzeige().getJobTitle());
                standort.setValue(applicationDTO.getStellenanzeige().getStandort());
                firstName.setValue(applicationDTO.getStudent().getFirstname());
                lastName.setValue(applicationDTO.getStudent().getLastname());
                email.setValue(((UserDTO) applicationDTO.getStudent()).getEmail());
                fachsemester.setValue(String.valueOf(applicationDTO.getStudent().getFachsemester()));
                beschaeftigung.setValue(applicationDTO.getBeschaeftigung());
                wohnort.setValue(applicationDTO.getWohnort());
                verfuegbar.setValue(dtf.format(applicationDTO.getVerfuegbar()));
                motivationsschreiben.setValue(applicationDTO.getMotivationsschreiben());
                lebenslauf.setValue(applicationDTO.getLebenslauf());
                verschicktAm.setValue(dtf.format(applicationDTO.getAppliedAt()));
                this.applicationDTO = applicationDTO;
            } else {
                Notification.show("ApplicationDTO ist null: ", 3000, Notification.Position.MIDDLE);
            }
        } catch (DatabaseLayerException e) {
            Notification.show("Fehler beim Abrufen der Application: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        } catch (NullPointerException e) {
            Notification.show("applicationID ist null: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
    }


}
