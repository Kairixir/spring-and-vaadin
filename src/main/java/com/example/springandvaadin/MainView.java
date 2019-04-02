package com.example.springandvaadin;

import com.example.springandvaadin.editor.CustomerEditor;
import com.example.springandvaadin.entity.Customer;
import com.example.springandvaadin.repository.CustomerRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

import java.awt.*;


@Route
public class MainView extends VerticalLayout {

    private final CustomerRepository repo;

    private final CustomerEditor editor;

    final Grid<Customer> grid;

    private TextField filterText = new TextField();

    private final Button addNewBtn;


    public MainView(CustomerRepository repo, CustomerEditor customerEditor){


        this.repo = repo;
        this.editor = customerEditor;
        this.grid = new Grid<>(Customer.class);
        this.addNewBtn = new Button("New customer", VaadinIcon.PLUS.create());


        //build layout
        HorizontalLayout actions = new HorizontalLayout(filterText,addNewBtn);

        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id","firstName", "lastName");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        filterText.setPlaceholder("Filter by last name");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e-> listCustomers(e.getValue()));

        // Instantiate and edit new Customer the new button is clicked
        grid.asSingleSelect().addValueChangeListener(e->editor.editCustomer(e.getValue()));

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e->editor.editCustomer(new Customer("","")));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(()->{
            editor.setVisible(false);
            listCustomers(filterText.getValue());
        });
        // Initialize listing
        listCustomers(null);

    }

    private void listCustomers(String filterText) {
        if (StringUtils.isEmpty(filterText)){
            grid.setItems(repo.findAll());
        }else{
            grid.setItems(repo.findByLastNameStartsWithIgnoringCase(filterText));

        }

    }
}
