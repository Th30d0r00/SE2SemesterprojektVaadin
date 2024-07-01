package org.hbrs.se2.project.hellocar.views;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "applicationdetailview", layout = AppView.class)
@PageTitle(value = "applicationdetailview")
public class ApplicationDetailView extends Div implements HasUrlParameter {

    public ApplicationDetailView() {

    }
    @Override
    public void setParameter(BeforeEvent beforeEvent, Object o) {

    }
}
