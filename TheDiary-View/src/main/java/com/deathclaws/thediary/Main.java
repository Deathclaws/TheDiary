package com.deathclaws.thediary;

import java.util.GregorianCalendar;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import com.deathclaws.thediary.model.Article;
import com.deathclaws.thediary.util.HibernateUtil;
import com.deathclaws.thediary.view.SearchScene;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) { 
    	launch(args);
    	logger.debug("launch");
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("The diary");
        SearchScene searchScene = SearchScene.create();
        primaryStage.setScene(searchScene);
        primaryStage.show();
	}

	@Override
	public void init() throws Exception {
		super.init();
		HibernateUtil.initFactory();
		doIndex();
		addRoger();
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		HibernateUtil.closeSessionFactory();
	}

	private static void doIndex() throws InterruptedException {
		EntityManager entityManager = HibernateUtil.getEntityManager();
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		fullTextEntityManager.createIndexer().startAndWait();
		fullTextEntityManager.close();
	}

	private static void addRoger() {
		//Session session = null;
		EntityManager entityManager = null;
		try {

			//session = HibernateUtil.getSession();
				entityManager = HibernateUtil.getEntityManager();
			entityManager.getTransaction().begin();
			
			//org.hibernate.Transaction transaction = session.beginTransaction();

			for(int i =0; i <5; i++) {
				Article a = new Article();
				a.setDate(GregorianCalendar.getInstance());
				a.setDescription("La description de l'enfer");
				a.setName("un article de ouf");
				// session.persist(a);
				entityManager.persist(a);
			}

			entityManager.getTransaction().commit();
			//transaction.commit();


		} catch (Exception ex) {
			entityManager.getTransaction().rollback();
			ex.printStackTrace();
		} finally{
			/*
			if(session != null) {
				session.close();
			}*/
			entityManager.close();
		}
	}

}
