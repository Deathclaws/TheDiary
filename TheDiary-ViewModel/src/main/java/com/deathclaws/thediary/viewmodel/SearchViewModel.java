package com.deathclaws.thediary.viewmodel;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

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

	public SearchViewModel() {
		searchTerm = new SimpleStringProperty();
		articleViewModels = FXCollections.observableArrayList();
		EventHandler<ActionEvent> searchEventHandler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				searchEventHandler(arg0);
			}
		};
		searchEventHandlerProperty = new SimpleObjectProperty<EventHandler<ActionEvent>>(searchEventHandler);
	}

	private void searchEventHandler(ActionEvent arg0) {
		EntityManager entityManager = HibernateUtil.getEntityManager();
		CriteriaBuilder criteriaBuilder = HibernateUtil.getCriteriaBuilder();
		CriteriaQuery<Article> criteriaQuery = criteriaBuilder.createQuery(Article.class);
		criteriaQuery.select(criteriaQuery.from(Article.class));
		List<Article> articles = entityManager.createQuery(criteriaQuery).getResultList();
		articleViewModels.clear();
		for (Article article : articles) {
			ArticleViewModel articleViewModel = new ArticleViewModel();
			articleViewModel.getIdentifier().setValue(article.getId());
			articleViewModel.getName().setValue(article.getName());
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

}
