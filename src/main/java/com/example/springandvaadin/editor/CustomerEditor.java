package com.example.springandvaadin.editor;

import com.example.springandvaadin.entity.Customer;
import com.example.springandvaadin.repository.CustomerRepository;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;


@SpringComponent
@UIScope
public class CustomerEditor extends VerticalLayout implements KeyNotifier {

    private final CustomerRepository repository;

    private Customer customer;

    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");

}
