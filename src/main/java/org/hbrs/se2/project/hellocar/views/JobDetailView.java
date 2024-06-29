package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.hellocar.dao.AnzeigeDAO;
import org.hbrs.se2.project.hellocar.util.Globals;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;

@Route(value = "job-detail", layout = AppView.class)
@PageTitle(value = "Job Detail")
public class JobDetailView extends Div implements HasUrlParameter<Integer> {
    //private final AnzeigeDAO anzeigeDAO;

    //private TextField

    //@Autowired
    public JobDetailView() {
        // Initialisierungscode
    }

    @Override
    public void setParameter(BeforeEvent event, Integer parameter) {
        int jobId = parameter;
        // Logik zur Anzeige der Details der Jobanzeige basierend auf jobId
    }
}
