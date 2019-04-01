package com.example.springandvaadin;

import com.example.springandvaadin.entity.Customer;
import com.example.springandvaadin.repository.CustomerRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

import java.awt.*;


@Route
public class MainView extends VerticalLayout {

    private final CustomerRepository repo;

    final Grid<Customer> grid;

    private TextField filterText = new TextField();



    public MainView(CustomerRepository repo){


        this.repo = repo;
        this.grid = new Grid<>(Customer.class);
        add(filterText, grid);

        filterText.setPlaceholder("Filter by last name");
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e-> listCustomers(e.getValue()));


    }

    private void listCustomers(String filterText) {
        if (StringUtils.isEmpty(filterText)){
            grid.setItems(repo.findAll());
        }else{
            grid.setItems(repo.findByLastNameStartsWithIgnoringCase(filterText));

        }

    }
}
