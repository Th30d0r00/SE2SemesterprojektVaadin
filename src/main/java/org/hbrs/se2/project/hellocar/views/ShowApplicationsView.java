package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.hbrs.se2.project.hellocar.control.ShowApplicationsControl;
import org.hbrs.se2.project.hellocar.dtos.*;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;

import java.util.List;


@Route(value = Globals.Pages.SHOW_MY_JOBPOSTINGS, layout = AppView.class)
@PageTitle("Eingegangene Bewerbungen")

public class ShowApplicationsView extends Div {
    private List<ApplicationDTO> applicationsList;
    public ShowApplicationsView(ShowApplicationsControl showApplicationsControl) {
        addClassName("Erhaltene Bewerbungen");
        try {
            UserDTO currentUser = (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
            applicationsList = showApplicationsControl.readApplications(currentUser.getId());

        } catch (DatabaseLayerException e) {
            Notification.show("Fehler beim Abrufen der Unternehmen: " + e.getMessage(), 3000, Notification.Position.MIDDLE);
        }
        add(this.createTitle());
        add(this.createGridTable());
    }

    private Component createTitle() {
        return new H3("Hier siehst du erhaltene Bewerbungen von Studenten");

    }

    private Component createGridTable() {
        Grid<ApplicationDTO> grid = new Grid<>();
        ListDataProvider<ApplicationDTO> dataProvider = new ListDataProvider<>(applicationsList);
        grid.setDataProvider(dataProvider);

        Grid.Column<ApplicationDTO> jobTitleColumn = grid.addColumn(ApplicationDTO::getJobTitle).setHeader("Stellenanzeige"); //Beim Name der Stellenanzeige sollte bei Initiativbewerbungen was anderes stehen
        Grid.Column<ApplicationDTO> studentFirstNameColumn = grid.addColumn(ApplicationDTO::getFirstname).setHeader("Vorname");
        Grid.Column<ApplicationDTO> studentLastNameColumn = grid.addColumn(ApplicationDTO::getLastname).setHeader("Nachname");
        Grid.Column<ApplicationDTO> studentEmailColumn = grid.addColumn(ApplicationDTO::getEmail).setHeader("E-Mail-Adresse");
        Grid.Column<ApplicationDTO> fachsemesterColumn = grid.addColumn(ApplicationDTO::getFachsemester).setHeader("Fachsemester");
        Grid.Column<ApplicationDTO> beschaeftigungColumn = grid.addColumn(ApplicationDTO::getBeschaeftigung).setHeader("Beschäftigung");
        Grid.Column<ApplicationDTO> wohnortColumn = grid.addColumn(ApplicationDTO::getWohnort).setHeader("Wohnort");

        grid.addItemClickListener(event -> {
            ApplicationDTO selectedApplication = event.getItem();
            //Weiterleitung zur Detailansicht einer erhaltenen Bewerbung -> annehmen / ablehnen
            UI.getCurrent().navigate(ApplicationDetailView.class, selectedApplication.getId());
        });

        HeaderRow filterRow = grid.appendHeaderRow();

        //Filter für Stellenanzeige
        TextField jobTitleField = new TextField();
        jobTitleField.addValueChangeListener(event -> dataProvider.addFilter(
                application -> StringUtils.containsIgnoreCase(application.getJobTitle(), jobTitleField.getValue())));
        jobTitleField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(jobTitleColumn).setComponent(jobTitleField);
        jobTitleField.setSizeFull();
        jobTitleField.setPlaceholder("Filter nach Job Titel");

        //Filter für Vorname
        TextField firstNameField = new TextField();
        jobTitleField.addValueChangeListener(event -> dataProvider.addFilter(
                application -> StringUtils.containsIgnoreCase(application.getFirstname(), firstNameField.getValue())));
        firstNameField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(studentFirstNameColumn).setComponent(firstNameField);
        firstNameField.setSizeFull();
        firstNameField.setPlaceholder("Filter nach Vorname");

        //Filter für Nachname
        TextField lastNameField = new TextField();
        jobTitleField.addValueChangeListener(event -> dataProvider.addFilter(
                application -> StringUtils.containsIgnoreCase(application.getLastname(), lastNameField.getValue())));
        lastNameField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(studentLastNameColumn).setComponent(lastNameField);
        lastNameField.setSizeFull();
        lastNameField.setPlaceholder("Filter nach Nachname");

        //Filter nach Beschaeftigung
        TextField beschaeftigungField = new TextField();
        beschaeftigungField.addValueChangeListener(event -> dataProvider.addFilter(
                application -> StringUtils.containsIgnoreCase(application.getBeschaeftigung(), beschaeftigungField.getValue())));
        beschaeftigungField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(beschaeftigungColumn).setComponent(beschaeftigungField);
        beschaeftigungField.setSizeFull();
        beschaeftigungField.setPlaceholder("Filter nach Beschäftigung");

        return grid;
    }
}
