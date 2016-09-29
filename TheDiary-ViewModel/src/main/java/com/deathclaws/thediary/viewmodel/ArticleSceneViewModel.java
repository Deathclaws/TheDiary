package com.deathclaws.thediary.viewmodel;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringEscapeUtils;

import com.deathclaws.thediary.messages.ArticleChangeMessage;
import com.deathclaws.thediary.messaging.ActionMessage;
import com.deathclaws.thediary.messaging.Messenger;
import com.deathclaws.thediary.model.Article;
import com.deathclaws.thediary.util.HibernateUtil;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ArticleSceneViewModel {

	private final Property<EventHandler<ActionEvent>> searchEventHandlerProperty;

	private final StringProperty nameProperty;
	
	private final StringProperty descriptionProperty;

	private final StringProperty htmlProperty;

	public ArticleSceneViewModel() {
		nameProperty = new SimpleStringProperty();
		htmlProperty = new SimpleStringProperty();
		descriptionProperty = new SimpleStringProperty();
		
		EventHandler<ActionEvent> searchEventHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				
			}
		};
		searchEventHandlerProperty = new SimpleObjectProperty<EventHandler<ActionEvent>>(searchEventHandler);
		
		descriptionProperty.addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				htmlProperty.set(translateHtmlDescription(newValue));
			}
		});
		
		ActionMessage<ArticleChangeMessage> actionMessage = new ActionMessage<ArticleChangeMessage>() {
			public void invoke(ArticleChangeMessage arg) {
				searchEventHandler(arg);
			}
		};
		Messenger.defaut.register(ArticleChangeMessage.class, actionMessage);
	}

	private String translateHtmlDescription(String in) {
		String html = StringEscapeUtils.escapeHtml4(in);
		
		MarkupParser markupParser = new MarkupParser();
		markupParser.setMarkupLanguage(new TextileLanguage());
		String htmlContent = markupParser.parseToHtml(markupContent);
		
		return html;
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

	public StringProperty getHtmlProperty() {
		return htmlProperty;
	}
	
}
