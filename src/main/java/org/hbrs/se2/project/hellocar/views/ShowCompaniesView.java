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
import org.hbrs.se2.project.hellocar.control.ShowCompaniesControl;
import org.hbrs.se2.project.hellocar.dao.AnzeigeDAO;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.dtos.CompanyDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.hbrs.se2.project.hellocar.views.AppView;
import org.hbrs.se2.project.hellocar.views.JobDetailView;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = Globals.Pages.SHOW_COMPANIES, layout = AppView.class)
@PageTitle(value = "showcompanies")
public class ShowCompaniesView extends Div{

    private List<CompanyDTO> companiesList;

    public ShowCompaniesView (ShowCompaniesControl companiesControl) throws DatabaseLayerException {
        addClassName("show-companies-view");

        // Auslesen aller abgespeicherten Jobanzeigen aus der DB (über das Control)
        companiesList = companiesControl.readAllCompanies();

        // Titel überhalb der Tabelle
        add(this.createTitle());

        // Hinzufügen der Tabelle (bei Vaadin: ein Grid)
        add(this.createGridTable());
    }
    private Component createTitle() {
        return new H3("Finde Unternehmen für eine Initiativbewerbung");
    }
    private Component createGridTable() {
        Grid<CompanyDTO> grid = new Grid<>();

        // Befüllen der Tabelle mit den zuvor ausgelesenen Jobanzeigen
        ListDataProvider<CompanyDTO> dataProvider = new ListDataProvider<>(companiesList);
        grid.setDataProvider(dataProvider);

        Grid.Column<CompanyDTO> nameColumn = grid.addColumn(CompanyDTO::getCompanyName).setHeader("Unternehmensname");
        Grid.Column<CompanyDTO> locationsColumn = grid.addColumn(CompanyDTO::getStandorte).setHeader("Standorte");
        Grid.Column<CompanyDTO> foundingdateColumn = grid.addColumn(CompanyDTO::getFoundingDate).setHeader("Gründungsdatum");
        Grid.Column<CompanyDTO> employeesColumn = grid.addColumn(CompanyDTO::getEmployees).setHeader("Anzahl der Mitarbeiter");

        // Click listener for rows
        grid.addItemClickListener(event -> {
            CompanyDTO selectedcompany = event.getItem();
            // Redirect to job detail view
            //UI.getCurrent().navigate(JobDetailView.class, selectedJob.getId()); //companyDetailView
        });

        HeaderRow filterRow = grid.appendHeaderRow();

        // Filter for company name
        TextField nameField = new TextField();
        nameField.addValueChangeListener(event -> dataProvider.addFilter(
                company -> StringUtils.containsIgnoreCase(company.getCompanyName(), nameField.getValue())));
        nameField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(nameColumn).setComponent(nameField);
        nameField.setSizeFull();
        nameField.setPlaceholder("Filter");

        // Filter for Standort
        TextField standortField = new TextField();
        standortField.addValueChangeListener(event -> dataProvider.addFilter(
                company -> StringUtils.containsIgnoreCase(company.getStandorte(), standortField.getValue())));
        standortField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(locationsColumn).setComponent(standortField);
        standortField.setSizeFull();
        standortField.setPlaceholder("Filter");

        // Filter nach Employees funktioniert aktuell nicht wegen Typumwandlung
        /*
        // Filter for employees
        TextField employeesField = new TextField();
        employeesField.addValueChangeListener(event -> dataProvider.addFilter(
                company -> StringUtils.containsIgnoreCase(Character.toString((char)company.getEmployees()), employeesField.getValue())));
        employeesField.setValueChangeMode(ValueChangeMode.EAGER);
        filterRow.getCell(employeesColumn).setComponent(employeesField);
        employeesField.setSizeFull();
        employeesField.setPlaceholder("Filter");
        */


        return grid;
    }
}
