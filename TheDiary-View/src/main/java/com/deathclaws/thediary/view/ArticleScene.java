package com.deathclaws.thediary.view;

import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;

public class ArticleScene extends Scene {

    public static ArticleScene create() {
    	VBox vbox = new VBox();
    	return new ArticleScene(vbox);
    }

	private ArticleScene(VBox vbox) {
		super(vbox);
		final HTMLEditor htmlEditor = new HTMLEditor();
		final TextField textField = new TextField();
		htmlEditor.lookup("MenuButton");
		vbox.getChildren().addAll(textField, htmlEditor);
		VBox.setVgrow(htmlEditor, Priority.ALWAYS);
	}
}
