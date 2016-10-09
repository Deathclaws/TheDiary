package com.deathclaws.thediary.viewmodel;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.DatabaseRetrievalMethod;
import org.hibernate.search.query.ObjectLookupMethod;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.deathclaws.thediary.messages.ArticleChangeMessage;
import com.deathclaws.thediary.messaging.Messenger;
import com.deathclaws.thediary.model.Article;
import com.deathclaws.thediary.util.HibernateUtil;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class SearchViewModel {

	private final Property<EventHandler<ActionEvent>> searchEventHandlerProperty;

	private final ObservableList<ArticleViewModel> articleViewModels;

	private final StringProperty searchTerm;

	private final Property<EventHandler<ActionEvent>> createEventHandlerProperty;

	public SearchViewModel() {
		searchTerm = new SimpleStringProperty();
		articleViewModels = FXCollections.observableArrayList();
		EventHandler<ActionEvent> searchEventHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				searchEventHandler(arg0);
			}
		};
		EventHandler<ActionEvent> createEventHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				createEventHandler(arg0);
			}
		};
		searchEventHandlerProperty = new SimpleObjectProperty<EventHandler<ActionEvent>>(searchEventHandler);
		createEventHandlerProperty = new SimpleObjectProperty<EventHandler<ActionEvent>>(createEventHandler);
	}

	private void createEventHandler(ActionEvent arg0) {
		Messenger.defaut.send(new ArticleChangeMessage());
	}
	
	@SuppressWarnings("unchecked")
	private void searchEventHandler(ActionEvent arg0) {
		String text = searchTerm.get();
		EntityManager entityManager = HibernateUtil.getEntityManager();
		
		List<Article> articles = null;
		if(!"".equals(text)) {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
			final QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Article.class).get();
			final Query query = queryBuilder.keyword().fuzzy().onFields("description").andField("name").matching(text).createQuery();
			final FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, Article.class);
			fullTextQuery.initializeObjectsWith(ObjectLookupMethod.SKIP, DatabaseRetrievalMethod.FIND_BY_ID);
			articles = fullTextQuery.getResultList();
		} else {
			CriteriaBuilder criteriaBuilder = HibernateUtil.getCriteriaBuilder();
			CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
			criteriaQuery.select(criteriaQuery.from(Article.class));
			articles = entityManager.createQuery(criteriaQuery).getResultList();
		}
		
		articleViewModels.clear();
		for (Article article : articles) {
			ArticleViewModel articleViewModel = new ArticleViewModel();
			articleViewModel.getIdentifier().setValue(article.getId());
			articleViewModel.getName().setValue(article.getName());
			articleViewModel.getDate().setValue(article.getDate());
			articleViewModels.add(articleViewModel);
		}
	}

	public ObservableList<ArticleViewModel> getArticleViewModels() {
		return articleViewModels;
	}

	public StringProperty getSearchTerm() {
		return searchTerm;
	}

	public Property<EventHandler<ActionEvent>> onSearch() {
		return searchEventHandlerProperty;
	}

	public Property<EventHandler<ActionEvent>> onCreate() {
		return createEventHandlerProperty;
	}

}
