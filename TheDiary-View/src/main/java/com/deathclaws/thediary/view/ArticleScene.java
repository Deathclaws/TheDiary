package com.deathclaws.thediary.view;

import com.deathclaws.thediary.viewmodel.ArticleSceneViewModel;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class ArticleScene extends Scene {

    public static ArticleScene create() {
    	VBox vbox = new VBox();
    	return new ArticleScene(vbox);
    }

	private ArticleScene(VBox vbox) {
		super(vbox);
		ArticleSceneViewModel model = new ArticleSceneViewModel();
		final TextArea htmlEditor = new TextArea();
		final TextField textField = new TextField();
		htmlEditor.lookup("MenuButton");
		vbox.getChildren().addAll(textField, htmlEditor);
		VBox.setVgrow(htmlEditor, Priority.ALWAYS);
		
		textField.textProperty().bindBidirectional(model.getNameProperty());
		htmlEditor.textProperty().bindBidirectional(model.getDescriptionProperty());
	}
}
