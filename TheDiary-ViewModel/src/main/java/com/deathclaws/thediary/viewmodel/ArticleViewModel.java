package com.deathclaws.thediary.viewmodel;

import java.time.LocalDate;

import com.deathclaws.thediary.messages.ArticleChangeMessage;
import com.deathclaws.thediary.messaging.Messenger;

import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class ArticleViewModel {

	private final LongProperty identifier;
    private final StringProperty name;
    private final ObjectProperty<LocalDate> date;
    private final Property<EventHandler<ActionEvent>> eventHandlerButton;
    private final ObjectProperty<Button> button;
    
    public ArticleViewModel() {
    	final ArticleViewModel ref = this;
    	EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Messenger.defaut.send(new ArticleChangeMessage(ref.getIdentifier().getValue()));
			}
		};
		identifier = new SimpleLongProperty();
    	name = new SimpleStringProperty();
    	date = new SimpleObjectProperty<LocalDate>();
    	button = new SimpleObjectProperty<Button>();
    	eventHandlerButton = new SimpleObjectProperty<EventHandler<ActionEvent>>(handler);
    }

	public StringProperty getName() {
		return name;
	}

	public ObjectProperty<LocalDate> getDate() {
		return date;
	}

	public ObjectProperty<Button> getButton() {
		return button;
	}

	public Property<EventHandler<ActionEvent>> getActionButtonProperty() {
		return eventHandlerButton;
	}

	public LongProperty getIdentifier() {
		return identifier;
	}
	
}
