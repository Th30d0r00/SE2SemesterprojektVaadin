package org.hbrs.se2.project.hellocar.views;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.hellocar.components.avataritem.AvatarItem;
import org.hbrs.se2.project.hellocar.control.SucheControl;
import org.hbrs.se2.project.hellocar.dtos.StudentDTO;
import org.hbrs.se2.project.hellocar.dtos.SucheDTO;
import org.hbrs.se2.project.hellocar.dtos.impl.StudentDTOImpl;
import org.hbrs.se2.project.hellocar.dtos.impl.SucheDTOImpl;
import org.hbrs.se2.project.hellocar.entities.Anzeige;
import org.hbrs.se2.project.hellocar.entities.Student;
import org.hbrs.se2.project.hellocar.services.db.exceptions.DatabaseLayerException;
import org.hbrs.se2.project.hellocar.util.Globals;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import com.vaadin.flow.component.button.Button;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import java.util.ArrayList;
import java.util.List;


@Route(value = "show-students", layout = AppView.class)
@PageTitle("show Student")

public class SearchStudentView extends Composite<VerticalLayout> {
    /*

    //Unternehmen sucht Student - SuchView


    private final SucheControl sucheControl;
    private TextField firstname;
    private TextField lastname;
    private ListBox<StudentDTO> avatarItems;
    private TextField input;

    public SearchStudentView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        MenuBar menuBar = new MenuBar();
        HorizontalLayout layoutRow2 = new HorizontalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        input = new TextField();
        Button searchButton = new Button("Suche", event -> {performSearch();});
        HorizontalLayout layoutRow3 = new HorizontalLayout();
        firstname = new TextField();
        lastname = new TextField();
        Hr hr = new Hr();
        avatarItems = new ListBox<>();
        Hr hr2 = new Hr();
        VerticalLayout layoutColumn4 = new VerticalLayout();


        sucheControl = new SucheControl();

        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.setHeight("min-content");
        layoutRow2.addClassName(Gap.MEDIUM);
        layoutRow2.setWidth("100%");
        layoutRow2.getStyle().set("flex-grow", "1");
        layoutColumn3.getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "2");
        layoutColumn2.setJustifyContentMode(JustifyContentMode.START);
        layoutColumn2.setAlignItems(Alignment.CENTER);
        input.setWidth("100%");
        layoutRow3.setWidthFull();
        layoutColumn2.setFlexGrow(1.0, layoutRow3);
        layoutRow3.addClassName(Gap.MEDIUM);
        layoutRow3.setWidth("100%");
        layoutRow3.getStyle().set("flex-grow", "2");
        firstname.setLabel("Vorname");
        firstname.setWidth("min-content");
        lastname.setLabel("Nachname");
        lastname.setWidth("min-content");
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
        layoutRow3.add(firstname);
        layoutRow3.add(lastname);
        //layoutRow3.add(searchButton);
        layoutColumn2.add(hr);
        layoutColumn2.add(avatarItems);
        layoutColumn2.add(hr2);
        layoutRow2.add(layoutColumn4);

        //performSearch();
    }

    record SampleItem(String value, String label, Boolean disabled) {
    }


    private void performSearch() {
        try{
            SucheDTOImpl sucheDTO = new SucheDTOImpl();
            sucheDTO.setFirstname(firstname.getValue());
            sucheDTO.setLastname(lastname.getValue());

            List<StudentDTO> ergebnisse = sucheControl.searchStudent(sucheDTO);
            setAvatarItemsSampleData(ergebnisse);
        }catch (DatabaseLayerException e){
            System.out.println("Fehler beim Suchen aufgetreten");
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

    private void setAvatarItemsSampleData(List<StudentDTO> studentList) {
        avatarItems.setItems(studentList);
        avatarItems.setRenderer(new ComponentRenderer<>(student -> {
            AvatarItem avatarItem = new AvatarItem(student.getFirstname(),
                    new Avatar(student.getLastname()));
            avatarItem.getContent().addClickListener(e -> {
                UI.getCurrent().navigate("http://localhost:8080/main");
            });
            return avatarItem;
        }));
    }
*/
}
