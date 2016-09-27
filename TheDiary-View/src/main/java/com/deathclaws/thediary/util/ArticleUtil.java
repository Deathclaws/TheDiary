package com.deathclaws.thediary.util;

import com.deathclaws.thediary.viewmodel.ArticleViewModel;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public final class ArticleUtil {

	public static Callback<TableColumn.CellDataFeatures<ArticleViewModel,String>, ObservableValue<String>> callbackName() {
		return new Callback<TableColumn.CellDataFeatures<ArticleViewModel,String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<ArticleViewModel, String> param) {
				return param.getValue().getName();
			}
		};
	}

	public static Callback<CellDataFeatures<ArticleViewModel, Button>, ObservableValue<Button>> callbackButton() {
		return new Callback<TableColumn.CellDataFeatures<ArticleViewModel,Button>, ObservableValue<Button>>() {
			public ObservableValue<Button> call(CellDataFeatures<ArticleViewModel, Button> param) {
				Button button = new Button("Yoo");
				button.onActionProperty().bindBidirectional(param.getValue().getActionButtonProperty());
				return new SimpleObjectProperty<Button>(button);
			}
		};
	}

}
