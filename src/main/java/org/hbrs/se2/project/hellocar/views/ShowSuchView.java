package org.hbrs.se2.project.hellocar.views;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.hbrs.se2.project.hellocar.components.avataritem.AvatarItem;
import org.hbrs.se2.project.hellocar.control.SucheControl;
import org.hbrs.se2.project.hellocar.dtos.AnzeigeDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.SucheDTOImpl;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//@Route(value = Globals.Pages.SHOW_SEARCH, layout = AppView.class)
@PageTitle(value = "suche")
public class ShowSuchView extends Composite<VerticalLayout> {

    private SucheControl sucheControl;
    private MultiSelectComboBox<SampleItem> jobTypeComboBox;
    private MultiSelectComboBox<SampleItem> standortComboBox;
    private ListBox<AnzeigeDTO> avatarItems;
    private TextField input;

    public ShowSuchView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        MenuBar menuBar = new MenuBar();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        input = new TextField();
        Button searchButton = new Button("Suche", event -> performSearch());
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        jobTypeComboBox = new MultiSelectComboBox<>();
        standortComboBox = new MultiSelectComboBox<>();
        Hr hr = new Hr();
        avatarItems = new ListBox<>();
        Hr hr2 = new Hr();
        VerticalLayout layoutColumn4 = new VerticalLayout();


        sucheControl = new SucheControl();

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        menuBar.setWidth("min-content");
        setMenuBarSampleData(menuBar);
        layoutRow2.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutColumn3.getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "2");
        layoutColumn2.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
        layoutColumn2.setAlignItems(FlexComponent.Alignment.CENTER);
        input.setWidth("100%");
        layoutRow3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(LumoUtility.Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.getStyle().set("flex-grow", "2");
        jobTypeComboBox.setLabel("Jobtyp");
        jobTypeComboBox.setWidth("min-content");
        setMultiSelectComboBoxSampleData(jobTypeComboBox);
        standortComboBox.setLabel("Standort");
        standortComboBox.setWidth("min-content");
        setMultiSelectComboBoxSampleDataStandort(standortComboBox);
        avatarItems.setWidth("100%");
        layoutColumn4.getStyle().set("flex-grow", "1");

        getContent().add(layoutRow);
        layoutRow.add(menuBar);
        getContent().add(layoutRow2);
        layoutRow2.add(layoutColumn3);
        layoutRow2.add(layoutColumn2);
        layoutColumn2.add(input);
        layoutColumn2.add(searchButton);
        layoutColumn2.add(layoutRow3);
        layoutRow3.add(jobTypeComboBox);
        layoutRow3.add(standortComboBox);
        //layoutRow3.add(searchButton);
        layoutColumn2.add(hr);
        layoutColumn2.add(avatarItems);
        layoutColumn2.add(hr2);
        layoutRow2.add(layoutColumn4);

        performSearch();
    }

    private void setMenuBarSampleData(MenuBar menuBar) {
        menuBar.addItem("Main");
        menuBar.addItem("Suche");
        menuBar.addItem("Profil");
        menuBar.addItem("Logout");
    }

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setMultiSelectComboBoxSampleData(MultiSelectComboBox<SampleItem> multiSelectComboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("Vollzeit", "Vollzeit", null));
        sampleItems.add(new SampleItem("Teilzeit", "Teilzeit", null));
        sampleItems.add(new SampleItem("Werkstudent", "Werkstudent", null));
        sampleItems.add(new SampleItem("Praktikum", "Praktikum", null));
        sampleItems.add(new SampleItem("Minijob", "Minijob", null));
        multiSelectComboBox.setItems(sampleItems);
        multiSelectComboBox.setItemLabelGenerator(SampleItem::label);
    }

    private void setMultiSelectComboBoxSampleDataStandort(MultiSelectComboBox<SampleItem> multiSelectComboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("Köln", "Köln", null));
        sampleItems.add(new SampleItem("Dortmund", "Dortmund", null));
        sampleItems.add(new SampleItem("Frankfurt", "Frankfurt", null));
        sampleItems.add(new SampleItem("Düsseldorf", "Düsseldorf", null));
        sampleItems.add(new SampleItem("Hamburg", "Hamburg", null));
        sampleItems.add(new SampleItem("Stuttgart", "Stuttgart", null));
        sampleItems.add(new SampleItem("München", "München", null));
        sampleItems.add(new SampleItem("Bonn", "Bonn", null));
        multiSelectComboBox.setItems(sampleItems);
        multiSelectComboBox.setItemLabelGenerator(SampleItem::label);
    }

    private void performSearch() {
        SucheDTOImpl sucheDTO = new SucheDTOImpl();
        sucheDTO.setJobTitle(input.getValue());
        sucheDTO.setJobType(getSelectedValues(jobTypeComboBox));
        sucheDTO.setStandort(getSelectedValues(standortComboBox));

        System.out.println("Try fängt gleich an");
        try {
            System.out.println("In try drinne");
            List<AnzeigeDTO> ergebnisse = sucheControl.sucheAnzeigen(sucheDTO);
            setAvatarItemsSampleData(ergebnisse);
        }catch (DatabaseLayerException e){
            System.out.println("Anzahl der Ergebnis: " + e.getMessage());
            System.out.println("Hier ist etwas schief gelaufen!!!");
        }
    }

    private List<String> getSelectedValues(MultiSelectComboBox<SampleItem> comboBox) {
        Set<SampleItem> selectedItems = comboBox.getSelectedItems();
        List<String> values = new ArrayList<>();
        for (SampleItem item : selectedItems) {
            values.add(item.value());
        }
        return values;
    }

    private void setAvatarItemsSampleData(List<AnzeigeDTO> anzeigeList) {
        avatarItems.setItems(anzeigeList);
        avatarItems.setRenderer(new ComponentRenderer<>(anzeige -> {
            AvatarItem avatarItem = new AvatarItem(anzeige.getJobTitle(),
                    anzeige.getCompany() + " - " + anzeige.getStandort(), formatPublicationDate(anzeige.getPublicationDate()),
                    new Avatar(anzeige.getJobTitle()));
            avatarItem.getContent().addClickListener(e -> {
                UI.getCurrent().navigate("http://localhost:8080/main");
            });
            return avatarItem;
        }));
    }

    private String formatPublicationDate(LocalDateTime publicationDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(publicationDate, now);

        long days = duration.toDays();
        long hours = duration.toHours();
        long minutes = duration.toMinutes();

        if (days > 0) {
            return "vor " + (days == 1 ? "1 Tag" : days + " Tagen");
        } else if (hours > 0) {
            return "vor " + (hours == 1 ? "1 Stunde" : hours + " Stunden");
        } else {
            return "vor " + (minutes == 1 ? "1 Minute" : minutes + " Minuten");
        }
    }
}
