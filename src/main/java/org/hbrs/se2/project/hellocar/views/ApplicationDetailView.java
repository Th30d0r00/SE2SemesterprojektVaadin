package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.open.App;
import org.hbrs.se2.project.hellocar.dao.ApplicationDAO;
import org.hbrs.se2.project.hellocar.dao.UserDAO;
import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import com.vaadin.flow.component.textfield.TextField;

@Route(value = "applicationdetailview", layout = AppView.class)
@PageTitle(value = "Bewerbungsdetails")
public class ApplicationDetailView extends VerticalLayout implements HasUrlParameter<Integer> {
    private final ApplicationDAO applicationDAO;
    private final UserDAO userDAO;

    private TextField jobTitle;
    private TextField standort;
    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField fachsemester;
    private TextField beschaeftigung;
    private TextField wohnort;
    private TextField verfuegbar;
    private TextField motivationsschreiben;
    private TextField lebenslauf;
    private TextField verschicktAm;

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
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Integer applicationId) {
        if(applicationId == null) {
            System.out.println("Null Value is not supported");
        }
        //ApplicationDTO applicationDTO = applicationDAO.get
    }


}
