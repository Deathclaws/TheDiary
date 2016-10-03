package com.deathclaws.thediary.view;

import com.deathclaws.thediary.viewmodel.ArticleSceneViewModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

public class ArticleScene extends Scene {

    public static ArticleScene create() {
    	final VBox vbox = new VBox();
    	return new ArticleScene(vbox);
    }

	private ArticleScene(VBox vbox) {
		super(vbox);
		ArticleSceneViewModel model = new ArticleSceneViewModel();
		final TextArea textarea = new TextArea();
		final TextField textField = new TextField();
		final WebView webView = new WebView();
		final HBox hBox = new HBox();
		final Button editButton = new Button("Editer");
		final Button saveButton = new Button("Sauver");
		final Button cancelButton = new Button("Annuler");
		hBox.getChildren().addAll(editButton, saveButton, cancelButton);
		hBox.setAlignment(Pos.CENTER_RIGHT);
		vbox.getChildren().addAll(textField, textarea, hBox, webView);
		VBox.setVgrow(webView, Priority.ALWAYS);
		VBox.setVgrow(textarea, Priority.ALWAYS);
		
		editButton.managedProperty().bind(editButton.visibleProperty());
		saveButton.managedProperty().bind(saveButton.visibleProperty());
		cancelButton.managedProperty().bind(cancelButton.visibleProperty());
		textarea.managedProperty().bind(textarea.visibleProperty());
		
		editButton.visibleProperty().bind(model.getEditableProperty().not());
		saveButton.visibleProperty().bind(model.getEditableProperty());
		cancelButton.visibleProperty().bind(model.getEditableProperty());
		textarea.visibleProperty().bind(model.getEditableProperty());
		
		textField.textProperty().bindBidirectional(model.getNameProperty());
		textarea.textProperty().bindBidirectional(model.getDescriptionProperty());
		
		editButton.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				model.getEditableProperty().set(true);
			}
		});
		/*
		saveButton.setOnMouseClicked(new EventHandler<Event>() {
			public void handle(Event event) {
				model.getEditableProperty().set(false);
			}
		});	
		*/
		saveButton.onActionProperty().bindBidirectional(model.getSaveEventhandlerProperty());
		
				
		
		model.getHtmlProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				webView.getEngine().loadContent(newValue);
			}
		});
		

	}
}
