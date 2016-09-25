package com.deathclaws.thediary.viewmodel;

import java.time.LocalDate;

import com.deathclaws.thediary.messages.ArticleChangeMessage;
import com.deathclaws.thediary.messaging.ActionMessage;
import com.deathclaws.thediary.messaging.Messenger;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;

public class ArticleViewModel {

    private final StringProperty name;
    private final ObjectProperty<LocalDate> date;
    private final ObjectProperty<Button> button;
    
    public ArticleViewModel() {
    	name = new SimpleStringProperty();
    	date = new SimpleObjectProperty<LocalDate>();
    	button = new SimpleObjectProperty<Button>();

    	ActionMessage<ArticleChangeMessage> actionArticleChangeMessage = new ActionMessage<ArticleChangeMessage>() {
			public void invoke(ArticleChangeMessage arg) {
				actionArticleChangeMessage();
			}
		};
    	Messenger.defaut.register(ArticleChangeMessage.class, actionArticleChangeMessage);
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

	public void actionArticleChangeMessage() {
		
	}
	
}
