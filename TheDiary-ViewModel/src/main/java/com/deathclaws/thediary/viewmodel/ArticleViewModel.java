package com.deathclaws.thediary.viewmodel;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ArticleViewModel {

    private final StringProperty name;
    private final ObjectProperty<LocalDate> date;

    public ArticleViewModel() {
    	name = new SimpleStringProperty();
    	date = new SimpleObjectProperty<LocalDate>();
    }

	public StringProperty getName() {
		return name;
	}

	public ObjectProperty<LocalDate> getDate() {
		return date;
	}

}
