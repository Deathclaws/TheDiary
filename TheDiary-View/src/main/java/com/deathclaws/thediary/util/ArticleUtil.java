package com.deathclaws.thediary.util;

import com.deathclaws.thediary.viewmodel.ArticleViewModel;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

	public static Callback<CellDataFeatures<ArticleViewModel, EventHandler<ActionEvent>>, ObservableValue<EventHandler<ActionEvent>>> callbackButton() {
		return new Callback<TableColumn.CellDataFeatures<ArticleViewModel,EventHandler<ActionEvent>>, ObservableValue<EventHandler<ActionEvent>>>() {
			public ObservableValue<EventHandler<ActionEvent>> call(CellDataFeatures<ArticleViewModel, EventHandler<ActionEvent>> param) {
				Button button = new Button("Yoo");
				return button.onActionProperty();
			}
		};
	}

}
