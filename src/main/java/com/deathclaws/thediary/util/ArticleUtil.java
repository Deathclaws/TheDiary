package com.deathclaws.thediary.util;

import com.deathclaws.thediary.model.Article;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public final class ArticleUtil {

	public static Callback<TableColumn.CellDataFeatures<Article,String>, ObservableValue<String>> callbackFirstName() {
		return new Callback<TableColumn.CellDataFeatures<Article,String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Article, String> param) {
				return param.getValue().getFirstName();
			}
		};
	}

	public static Callback<TableColumn.CellDataFeatures<Article,String>, ObservableValue<String>> callbackLastName() {
		return new Callback<TableColumn.CellDataFeatures<Article,String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Article, String> param) {
				return param.getValue().getLastName();
			}
		};
	}

}
