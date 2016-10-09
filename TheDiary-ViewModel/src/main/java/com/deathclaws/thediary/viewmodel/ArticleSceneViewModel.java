package com.deathclaws.thediary.viewmodel;

import java.util.GregorianCalendar;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringEscapeUtils;

import com.deathclaws.thediary.messages.ArticleChangeMessage;
import com.deathclaws.thediary.messaging.ActionMessage;
import com.deathclaws.thediary.messaging.Messenger;
import com.deathclaws.thediary.model.Article;
import com.deathclaws.thediary.util.HibernateUtil;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ArticleSceneViewModel {

	private final Property<EventHandler<ActionEvent>> searchEventHandlerProperty;

	private final LongProperty identifierProperty;
	
	private final StringProperty nameProperty;
	
	private final StringProperty descriptionProperty;

	private final StringProperty htmlProperty;

	private final BooleanProperty editableProperty;

	private final Property<EventHandler<ActionEvent>> saveEventhandlerProperty;

	private final Property<EventHandler<ActionEvent>> cancelEventhandlerProperty;

	public ArticleSceneViewModel() {
		identifierProperty = new SimpleLongProperty();
		nameProperty = new SimpleStringProperty("");
		htmlProperty = new SimpleStringProperty("");
		descriptionProperty = new SimpleStringProperty("");
		editableProperty = new SimpleBooleanProperty();
		
		EventHandler<ActionEvent> searchEventHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				
			}
		};
		EventHandler<ActionEvent> saveEventHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				saveEventHandler(arg0);
			}
		};
		EventHandler<ActionEvent> cancelEventHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				cancelEventHandler(arg0);
			}
		};
		
		searchEventHandlerProperty = new SimpleObjectProperty<EventHandler<ActionEvent>>(searchEventHandler);
		saveEventhandlerProperty = new SimpleObjectProperty<EventHandler<ActionEvent>>(saveEventHandler);
		cancelEventhandlerProperty = new SimpleObjectProperty<EventHandler<ActionEvent>>(cancelEventHandler);
		
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
		return html;
	}
	
	private void cancelEventHandler(ActionEvent arg0) {
		loadData(identifierProperty.get());
	}
	
	private void saveEventHandler(ActionEvent arg0) {
		EntityManager entityManager = HibernateUtil.getEntityManager();
		try {
			entityManager.getTransaction().begin();
			Article article = entityManager.find(Article.class, identifierProperty.get());
			if(article == null) { 
				article = new Article(); 
				article.setDate(new GregorianCalendar());
			}
			article.setName(nameProperty.get());
			article.setDescription(descriptionProperty.get());
			Article mergeArticle = entityManager.merge(article);
			entityManager.getTransaction().commit();
			identifierProperty.set(mergeArticle.getId());
			editableProperty.set(false);
		} catch (Exception ex) {
			entityManager.getTransaction().rollback();
			throw ex;
		} finally{
			entityManager.close();
		}
	}
	
	private void searchEventHandler(ArticleChangeMessage arg0) {
		Long identifier = arg0.getIdentifier();
		loadData(identifier);
	}

	private void loadData(long identifier) {
		if(identifier != 0) {
			EntityManager entityManager = HibernateUtil.getEntityManager();
			Article article = entityManager.find(Article.class, identifier);
			entityManager.close();
			descriptionProperty.setValue(article.getDescription());
			nameProperty.setValue(article.getName());
			identifierProperty.set(article.getId());
			editableProperty.set(false);
		} else {
			descriptionProperty.setValue("");
			nameProperty.setValue("");
			identifierProperty.set(0);
			editableProperty.set(true);
		}
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

	public BooleanProperty getEditableProperty() {
		return editableProperty;
	}

	public Property<EventHandler<ActionEvent>> getSaveEventhandlerProperty() {
		return saveEventhandlerProperty;
	}

	public LongProperty getIdentifierProperty() {
		return identifierProperty;
	}

	public Property<EventHandler<ActionEvent>> getCancelEventhandlerProperty() {
		return cancelEventhandlerProperty;
	}
	
}
