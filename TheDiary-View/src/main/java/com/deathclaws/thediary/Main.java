package com.deathclaws.thediary;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
		
		Scene searchScene = ArticleScene.create();
		primaryStage.setScene(searchScene);
		primaryStage.show();

		Scene cds = SearchScene.create();
		Stage stage = new Stage();
		stage.setScene(cds);
		stage.show();
		
		stage.setTitle("The diary");
		primaryStage.setTitle("The diary");
		
		EventHandler<WindowEvent> eventHandler = new EventHandler<WindowEvent>() {
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
		};
		
		stage.setOnCloseRequest(eventHandler);
		primaryStage.setOnCloseRequest(eventHandler);
	}

	@Override
	public void init() throws Exception {
		super.init();
		HibernateUtil.initFactory();
	}

	@Override
	public void stop() throws Exception {
		super.stop();
		HibernateUtil.closeSessionFactory();
	}

}
