package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.hellocar.dao.AnzeigeDAO;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;

/*
 * Ansicht zur Anzeige von Jobdetails für den Studenten
 * */

@Route(value = Globals.Pages.JOB_DETAIL, layout = AppView.class)
@PageTitle(value = "Job Details")
@CssImport("./styles/views/jobdetail/show-job-detail-view.css")
public class JobDetailView extends Div implements HasUrlParameter<Integer> {
    private final AnzeigeDAO anzeigeDAO;
    private TextField titelField;
    private TextField standortField;
    private TextField jobArtField;
    private TextArea stellenbeschreibungField;
    private TextField veroeffentlichungsdatumField;
    private Integer jobId;

    public JobDetailView() {
        this.anzeigeDAO = new AnzeigeDAO();

        // FormLayout zur Anordnung der Textboxen und Labels
        FormLayout formLayout = new FormLayout();

        // Erstellen der Textfelder und Labels
        titelField = new TextField();
        titelField.setLabel("Titel");

        standortField = new TextField();
        standortField.setLabel("Standort");

        jobArtField = new TextField();
        jobArtField.setLabel("Job Art");

        stellenbeschreibungField = new TextArea();
        stellenbeschreibungField.setLabel("Stellenbeschreibung");

        veroeffentlichungsdatumField = new TextField();
        veroeffentlichungsdatumField.setLabel("Veröffentlichungsdatum");

        // Füge die Textfelder zum Layout hinzu
        formLayout.add(titelField, standortField, jobArtField, veroeffentlichungsdatumField, stellenbeschreibungField);

        Button applyButton = new Button("Bewerben", e -> {
            // Logik zur Bewerbung auf die Jobanzeige
            UI.getCurrent().navigate(Globals.Pages.JOB_APPLY + "/" + jobId);
        });

        Button backButton = new Button("Zurück", e -> {
            UI.getCurrent().navigate(Globals.Pages.SHOW_JOBPOSTINGS);
        });

        applyButton.addClassName("apply-button");
        backButton.addClassName("back-button");

        HorizontalLayout buttonLayout = new HorizontalLayout(backButton, applyButton);

        // Hauptlayout mit Padding
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setPadding(true); // Fügt standardmäßiges Padding hinzu
        mainLayout.add(formLayout, buttonLayout);

        add(mainLayout);
    }

    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {
        // Setze die jobId basierend auf dem Parameter
        jobId = parameter;

        AnzeigeDTO jobAnzeige = null;
        try {
            // Versuche, die Jobanzeige anhand der jobId zu finden
            jobAnzeige = anzeigeDAO.findAnzeigeById(jobId);
        } catch (DatabaseLayerException e) {
            // Behandele eine Datenbankschicht-Ausnahme, indem eine RuntimeException geworfen wird
            throw new RuntimeException(e);
        }

        if (jobAnzeige != null) {
            // Setze die Werte der UI-Felder mit den Daten der gefundenen Jobanzeige
            titelField.setValue(jobAnzeige.getJobTitle());
            standortField.setValue(jobAnzeige.getStandort());
            jobArtField.setValue(jobAnzeige.getJobType());
            stellenbeschreibungField.setValue(jobAnzeige.getJobDescription());
            veroeffentlichungsdatumField.setValue(jobAnzeige.getPublicationDate().toString());
        } else {
            // Falls keine Jobanzeige gefunden wurde, wirf eine RuntimeException mit entsprechender Nachricht
            throw new RuntimeException("Jobanzeige mit ID " + jobId + " nicht gefunden!");
        }
    }
}
