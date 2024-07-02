package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;
import org.hbrs.se2.project.hellocar.control.JobPostingControl;
import org.hbrs.se2.project.hellocar.dao.AnzeigeDAO;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.views.AppView;
import org.hbrs.se2.project.hellocar.views.JobDetailView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = Globals.Pages.SHOW_JOBPOSTINGS, layout = AppView.class)
@PageTitle(value = "Verfügbare Stellenanzeigen")
public class ShowJobPostingsView extends Div {

    private List<AnzeigeDTO> jobList;


    public ShowJobPostingsView (JobPostingControl jobPostingControl) throws DatabaseLayerException {
        addClassName("job-postings-view");

        // Auslesen aller abgespeicherten Jobanzeigen aus der DB (über das Control)
        jobList = jobPostingControl.readAllJobPostings();

        // Titel überhalb der Tabelle
        add(this.createTitle());

        // Hinzufügen der Tabelle (bei Vaadin: ein Grid)
        add(this.createGridTable());
    }

    private Component createGridTable() {
        Grid<AnzeigeDTO> grid = new Grid<>();

        // Befüllen der Tabelle mit den zuvor ausgelesenen Jobanzeigen
        ListDataProvider<AnzeigeDTO> dataProvider = new ListDataProvider<>(jobList);
        grid.setDataProvider(dataProvider);

        Grid.Column<AnzeigeDTO> titelColumn = grid.addColumn(AnzeigeDTO::getJobTitle).setHeader("Titel");
        Grid.Column<AnzeigeDTO> standortColumn = grid.addColumn(AnzeigeDTO::getStandort).setHeader("Standort");
        Grid.Column<AnzeigeDTO> jobartColumn = grid.addColumn(AnzeigeDTO::getJobType).setHeader("Jobart");
        Grid.Column<AnzeigeDTO> veroeffentlichungColumn = grid.addColumn(AnzeigeDTO::getPublicationDate).setHeader("Veröffentlichung");

        // Click listener for rows
        grid.addItemClickListener(event -> {
            AnzeigeDTO selectedJob = event.getItem();
            // Redirect to job detail view
            UI.getCurrent().navigate(JobDetailView.class, selectedJob.getId());
        });

        HeaderRow filterRow = grid.appendHeaderRow();

        // Filter for Titel
        TextField titelField = new TextField();
        titelField.addValueChangeListener(event -> dataProvider.addFilter(
                job -> StringUtils.containsIgnoreCase(job.getJobTitle(), titelField.getValue())));
        titelField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(titelColumn).setComponent(titelField);
        titelField.setSizeFull();
        titelField.setPlaceholder("Filter");

        // Filter for Standort
        TextField standortField = new TextField();
        standortField.addValueChangeListener(event -> dataProvider.addFilter(
                job -> StringUtils.containsIgnoreCase(job.getStandort(), standortField.getValue())));
        standortField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(standortColumn).setComponent(standortField);
        standortField.setSizeFull();
        standortField.setPlaceholder("Filter");

        // Filter for Jobart
        TextField jobartField = new TextField();
        jobartField.addValueChangeListener(event -> dataProvider.addFilter(
                job -> StringUtils.containsIgnoreCase(job.getJobType(), jobartField.getValue())));
        jobartField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(jobartColumn).setComponent(jobartField);
        jobartField.setSizeFull();
        jobartField.setPlaceholder("Filter");

        return grid;
    }

    private Component createTitle() {
        return new H3("Suche nach Jobs");
    }
}