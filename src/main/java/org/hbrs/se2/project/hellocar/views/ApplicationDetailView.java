package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import com.vaadin.flow.component.textfield.TextField;

@Route(value = "applicationdetailview", layout = AppView.class)
@PageTitle(value = "Bewebrungsdetails")
public class ApplicationDetailView extends VerticalLayout implements HasUrlParameter {

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
    }

    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Integer applicationId) {
        if(applicationId == null) {
            System.out.println("Null Value not supported");
        }
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Object o) {

    }
}
