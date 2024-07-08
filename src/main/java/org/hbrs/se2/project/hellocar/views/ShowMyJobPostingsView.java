package org.hbrs.se2.project.hellocar.views;

import org.hbrs.se2.project.hellocar.control.JobPostingControl;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import java.util.List;
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
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;

@Route(value = Globals.Pages.SHOW_MY_JOBPOSTINGS, layout = AppView.class)
@PageTitle(value = "Meine Stellenanzeigen")
public class ShowMyJobPostingsView extends Div{

    private final List<AnzeigeDTO> anzeigenListe;


    public ShowMyJobPostingsView(JobPostingControl jobPostingControl) throws DatabaseLayerException {
        addClassName("show-my-jobpostings");
        UserDTO currentUser = (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
        System.out.print(currentUser.getId());
        anzeigenListe = jobPostingControl.readAllMyJobPostings(currentUser.getId());
        add(this.createTitle());
        add(this.createGridTable());
    }

    private Component createTitle() {
        return new H3("Hier siehst du deine Stellenanzeigen.");
    }

    private Component createGridTable() {
        Grid<AnzeigeDTO> grid = new Grid<>();

        // Befüllen der Tabelle mit den zuvor ausgelesenen Companies
        ListDataProvider<AnzeigeDTO> dataProvider = new ListDataProvider<>(anzeigenListe);
        grid.setDataProvider(dataProvider);

        Grid.Column<AnzeigeDTO> titleColumn = grid.addColumn(AnzeigeDTO::getJobTitle).setHeader("Titel");
        Grid.Column<AnzeigeDTO> jobTypeColumn = grid.addColumn(AnzeigeDTO::getJobType).setHeader("Art der Anstellung");
        Grid.Column<AnzeigeDTO> publishedColumn = grid.addColumn(AnzeigeDTO::getPublicationDate).setHeader("Veröffentlicht am");

        // Click listener for rows
        grid.addItemClickListener(event -> {
            AnzeigeDTO selectedJobPosting = event.getItem();
            UI.getCurrent().navigate(EditJobPostingView.class, selectedJobPosting.getId());
        });

        HeaderRow filterRow = grid.appendHeaderRow();

        // Filter for Titel der Anzeige
        TextField titleField = new TextField();
        titleField.addValueChangeListener(event -> dataProvider.addFilter(
                anzeige -> StringUtils.containsIgnoreCase(anzeige.getJobTitle(), titleField.getValue())));
        titleField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(titleColumn).setComponent(titleField);
        titleField.setSizeFull();
        titleField.setPlaceholder("Filter");

        // Filter für Art der Anstellung
        TextField jobTypeField = new TextField();
        jobTypeField.addValueChangeListener(event -> dataProvider.addFilter(
                anzeige -> StringUtils.containsIgnoreCase(anzeige.getJobType(), jobTypeField.getValue())));
        jobTypeField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(jobTypeColumn).setComponent(jobTypeField);
        jobTypeField.setSizeFull();
        jobTypeField.setPlaceholder("Filter");

        /*
        // Filter for Veröffentlichungsdatum
        TextField publishedField = new TextField();
        publishedField.addValueChangeListener(event -> dataProvider.addFilter(
                anzeige -> StringUtils.containsIgnoreCase((String) anzeige.getPublicationDate()), publishedField.getValue())));
        publishedField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(publishedColumn).setComponent(publishedField);
        publishedField.setSizeFull();
        publishedField.setPlaceholder("Filter");
        */


        return grid;
    }

}
