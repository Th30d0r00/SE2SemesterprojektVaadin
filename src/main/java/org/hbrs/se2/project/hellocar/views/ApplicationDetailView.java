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
import org.hbrs.se2.project.hellocar.control.ApplicationControl;
import org.hbrs.se2.project.hellocar.dao.ApplicationDAO;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import com.vaadin.flow.component.textfield.TextField;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Route(value = Globals.Pages.SHOW_APPLICATION_DETAILS, layout = AppView.class)
@PageTitle("Bewerbungsdetails")
@CssImport("./styles/views/applicationdetailview/application-detail-view.css")
public class ApplicationDetailView extends VerticalLayout implements HasUrlParameter<Integer> {
    private ApplicationDTO applicationDTO;
    private ApplicationControl applicationControl = new ApplicationControl();
    private final UserDAO userDAO;
    private final TextField jobTitle = new TextField();
    private final TextField standort = new TextField();
    private final TextField firstName = new TextField();
    private final TextField lastName = new TextField();
    private final TextField email = new TextField();
    private final TextField fachsemester = new TextField();
    private final TextField beschaeftigung = new TextField();
    private final TextField wohnort = new TextField();
    private final TextField verfuegbar = new TextField();
    private final TextField motivationsschreiben = new TextField();
    private final TextField lebenslauf = new TextField();
    private final TextField verschicktAm = new TextField();
    private final Button acceptButton = new Button("Bewerbung annehmen");
    private final Button refuseButton = new Button("Bewerbung ablehnen");
    private final Button backButton = new Button("Zurück");

    public ApplicationDetailView() {
        this.applicationControl = new ApplicationControl();
        this.userDAO = new UserDAO();

        // Form-Layout erstellen und hinzufügen
        add(createFormLayout());

        // Button-Layout erstellen und hinzufügen
        add(createButtonLayout());

        // Aktionen für die Buttons
        backButton.addClickListener(event -> {
            UI.getCurrent().navigate(Globals.Pages.SHOW_APPLICATIONS);
        });

        acceptButton.addClickListener(event -> {
            try {
                UserDTO currentUser = (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
                applicationControl.acceptApplication(applicationDTO.getId());
                Notification.show("Bewerbung erfolgreich angenommen", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().navigate(Globals.Pages.SHOW_APPLICATIONS);
            } catch (DatabaseLayerException e) {
                Notification.show("Fehler beim Annehmen der Bewerbung: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });

        refuseButton.addClickListener(event -> {
            try {
                UserDTO currentUser = (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
                applicationControl.refuseApplication(applicationDTO.getId());
                Notification.show("Bewerbung erfolgreich abgelehnt", 3000, Notification.Position.MIDDLE);
                UI.getCurrent().navigate(Globals.Pages.SHOW_APPLICATIONS);
            } catch (DatabaseLayerException e) {
                Notification.show("Fehler beim Ablehnen der Bewerbung: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            }
        });
    }

    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();

        jobTitle.setLabel("Job Titel");
        standort.setLabel("Standort");
        firstName.setLabel("Vorname");
        lastName.setLabel("Nachname");
        email.setLabel("E-Mail-Adresse");
        fachsemester.setLabel("Fachsemester");
        beschaeftigung.setLabel("Art der Beschäftigung");
        wohnort.setLabel("Wohnort");
        verfuegbar.setLabel("Verfügbar ab ...");
        motivationsschreiben.setLabel("Motivationsschreiben");
        lebenslauf.setLabel("Lebenslauf");
        verschicktAm.setLabel("Bewerbung abgeschickt am ...");

        formLayout.addFormItem(jobTitle, "Job Titel");
        formLayout.addFormItem(standort, "Standort");
        formLayout.addFormItem(firstName, "Vorname");
        formLayout.addFormItem(lastName, "Nachname");
        formLayout.addFormItem(email, "E-Mail-Adresse");
        formLayout.addFormItem(fachsemester, "Fachsemester");
        formLayout.addFormItem(beschaeftigung, "Art der Beschäftigung");
        formLayout.addFormItem(wohnort, "Wohnort");
        formLayout.addFormItem(verfuegbar, "Verfügbar ab");
        formLayout.addFormItem(motivationsschreiben, "Motivationsschreiben");
        formLayout.addFormItem(lebenslauf, "Lebenslauf");
        formLayout.addFormItem(verschicktAm, "Verschickt am");

        return formLayout;
    }

    private HorizontalLayout createButtonLayout() {
        backButton.addClassName("back-button");
        acceptButton.addClassName("accept-button");
        refuseButton.addClassName("refuse-button");

        return new HorizontalLayout(backButton, acceptButton, refuseButton);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Integer applicationId) {
        if (applicationId == null) {
            System.out.println("Null Value is not supported");
            return;
        }

        try {
            applicationDTO = applicationControl.findApplicationById(applicationId);
            final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MMMM uuuu", Locale.ENGLISH);

            if (applicationDTO.getStellenanzeige().getStandort() == null) {
                standort.setValue("offen");
            } else {
                standort.setValue(applicationDTO.getStellenanzeige().getStandort());
            }

            jobTitle.setValue(applicationDTO.getStellenanzeige().getJobTitle());
            firstName.setValue(applicationDTO.getStudent().getFirstname());
            lastName.setValue(applicationDTO.getStudent().getLastname());
            email.setValue(applicationDTO.getUser().getEmail());
            fachsemester.setValue(String.valueOf(applicationDTO.getStudent().getFachsemester()));
            beschaeftigung.setValue(applicationDTO.getBeschaeftigung());
            wohnort.setValue(applicationDTO.getWohnort());
            verfuegbar.setValue(dtf.format(applicationDTO.getVerfuegbar()));
            motivationsschreiben.setValue(applicationDTO.getMotivationsschreiben());
            lebenslauf.setValue(applicationDTO.getLebenslauf());
            verschicktAm.setValue(dtf.format(applicationDTO.getAppliedAt()));

        } catch (DatabaseLayerException e) {
            Notification.show("Fehler beim Abrufen der Application: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        } catch (NullPointerException e) {
            Notification.show("applicationID ist null: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
            System.out.println(applicationId);
        }
    }
}
