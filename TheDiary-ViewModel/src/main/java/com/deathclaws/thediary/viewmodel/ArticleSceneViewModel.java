package com.deathclaws.thediary.viewmodel;

import javax.persistence.EntityManager;

import com.deathclaws.thediary.messages.ArticleChangeMessage;
import com.deathclaws.thediary.messaging.ActionMessage;
import com.deathclaws.thediary.messaging.Messenger;
import com.deathclaws.thediary.model.Article;
import com.deathclaws.thediary.util.HibernateUtil;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ArticleSceneViewModel {

	private final Property<EventHandler<ActionEvent>> searchEventHandlerProperty;

	private final StringProperty nameProperty;
	
	private final StringProperty descriptionProperty;

	public ArticleSceneViewModel() {
		nameProperty = new SimpleStringProperty();
		descriptionProperty = new SimpleStringProperty();
		
		EventHandler<ActionEvent> searchEventHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				
			}
		};
		searchEventHandlerProperty = new SimpleObjectProperty<EventHandler<ActionEvent>>(searchEventHandler);
		
		ActionMessage<ArticleChangeMessage> actionMessage = new ActionMessage<ArticleChangeMessage>() {
			public void invoke(ArticleChangeMessage arg) {
				searchEventHandler(arg);
			}
		};
		Messenger.defaut.register(ArticleChangeMessage.class, actionMessage);
	}

	private void searchEventHandler(ArticleChangeMessage arg0) {
		EntityManager entityManager = HibernateUtil.getEntityManager();
		Article article = entityManager.find(Article.class, arg0.getIdentifier());
		descriptionProperty.setValue(article.getDescription());
		nameProperty.setValue(article.getName());
	}

	public StringProperty getNameProperty() {
		return nameProperty;
	}

	public StringProperty getDescriptionProperty() {
		return descriptionProperty;
	}

	public Property<EventHandler<ActionEvent>> onSearch() {
		return searchEventHandlerProperty;
	}

}
