package com.deathclaws.thediary.model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Article {

    private final StringProperty firstName;
    private final StringProperty lastName;
    private final ObjectProperty<LocalDate> birthday;


    public Article() {
    	firstName = new SimpleStringProperty("first");
    	lastName = new SimpleStringProperty("last");
    	birthday = new SimpleObjectProperty<LocalDate>();
    }

	public StringProperty getFirstName() {
		return firstName;
	}

	public StringProperty getLastName() {
		return lastName;
	}

	public ObjectProperty<LocalDate> getBirthday() {
		return birthday;
	}

}
