package com.deathclaws.thediary;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;

import com.deathclaws.thediary.util.HibernateUtil;
import com.deathclaws.thediary.view.ArticleScene;
import com.deathclaws.thediary.view.SearchScene;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private static final Logger logger = LogManager.getLogger(Main.class.getName());

	public static void main(String[] args) { 
		logger.debug("started");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("The diary");
		Scene searchScene = ArticleScene.create();
		primaryStage.setScene(searchScene);
		primaryStage.show();

		Scene cds = SearchScene.create();
		Stage stage = new Stage();
		stage.setScene(cds);
		stage.show();

		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent event) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText("Quitter ?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK){
					Platform.exit();
				}
				if(result.get()==ButtonType.CANCEL){
					alert.close();
				}
			}
		});
	}

	@Override
	public void init() throws Exception {
		super.init();
		HibernateUtil.initFactory();
		doIndex();
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
	/*
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
			entityManager.close();
		}
	}
	 */
}
