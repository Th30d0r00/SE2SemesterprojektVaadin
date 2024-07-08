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
import org.hbrs.se2.project.hellocar.control.ApplicationControl;
import org.hbrs.se2.project.hellocar.control.UserControl;
import org.hbrs.se2.project.hellocar.dtos.ApplicationDTO;
import org.hbrs.se2.project.hellocar.dtos.UserDTO;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Route(value = Globals.Pages.SHOW_MY_APPLICATIONS, layout = AppView.class)
@PageTitle("Meine Bewerbungen")
public class ShowMyApplications extends Div {

    private List<ApplicationDTO> applicationsList;

    public ShowMyApplications(ApplicationControl applicationControl) throws DatabaseLayerException {
        this.applicationsList = applicationControl.readMyApplications(getCurrentUser().getId());

        addClassName("my-applications");
        add(createTitle());
        add(createGridTable());
    }

    private Component createTitle() {
        return new H3("Hier siehst du deine Bewerbungen");
    }

    private Grid<ApplicationDTO> createGridTable() {
        Grid<ApplicationDTO> grid = new Grid<>();
        ListDataProvider<ApplicationDTO> dataProvider = new ListDataProvider<>(applicationsList);
        grid.setDataProvider(dataProvider);
        UserControl userControl = new UserControl();

        Grid.Column<ApplicationDTO> jobTitleColumn = grid.addColumn(application -> application.getStellenanzeige().getJobTitle())
                .setHeader("Stellenanzeige");
        Grid.Column<ApplicationDTO> companyColumn = grid.addColumn(application -> application.getCompany().getCompanyName())
                .setHeader("Unternehmen");
        grid.addColumn(ApplicationDTO::getStatus)
                .setHeader("Status");
        Grid.Column<ApplicationDTO> companyContactColumn = grid.addColumn(application -> {
                    try {
                        UserDTO user = userControl.findUser(application.getCompany().getId());
                        return user != null ? user.getEmail() : "N/A";
                    } catch (DatabaseLayerException | SQLException e) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Fehler beim Abrufen der Kontaktdaten", e);
                        return "Fehler beim Abrufen der Daten";
                    }
                })
                .setHeader("Kontaktdaten");

        return grid;
    }

    public UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }


}
