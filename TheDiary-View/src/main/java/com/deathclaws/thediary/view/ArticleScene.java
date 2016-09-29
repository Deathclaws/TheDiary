package com.deathclaws.thediary.view;

import com.deathclaws.thediary.viewmodel.ArticleSceneViewModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

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
		final WebView webView = new WebView();
		vbox.getChildren().addAll(textField, htmlEditor, webView);
		VBox.setVgrow(webView, Priority.ALWAYS);
		VBox.setVgrow(htmlEditor, Priority.ALWAYS);
		
		textField.textProperty().bindBidirectional(model.getNameProperty());
		htmlEditor.textProperty().bindBidirectional(model.getDescriptionProperty());
		
		model.getHtmlProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				webView.getEngine().loadContent(newValue);
			}
		});
	}
}
