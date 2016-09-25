package com.deathclaws.thediary.view;

import com.deathclaws.thediary.util.ArticleUtil;
import com.deathclaws.thediary.viewmodel.ArticleViewModel;
import com.deathclaws.thediary.viewmodel.SearchViewModel;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
/*
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
*/
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class SearchScene extends Scene {

    public static SearchScene create() {
    	VBox vbox = new VBox();
    	return new SearchScene(vbox);
    }

	private SearchScene(VBox vbox) {
        super(vbox);
        final SearchViewModel searchViewModel = new SearchViewModel();
        final HBox hBox = new HBox();
        final Button buttonSearch = new Button("Search");
        final TextField textField = new TextField();
        final Button buttonCreate = new Button("Create");
        final TableView<ArticleViewModel> table = new TableView<ArticleViewModel>();
        vbox.setAlignment(Pos.CENTER_RIGHT);
        hBox.getChildren().addAll(textField, buttonSearch);
        HBox.setHgrow(textField, Priority.ALWAYS);
        table.setEditable(true);
        
        TableColumn<ArticleViewModel, String> firstNameCol = new TableColumn<ArticleViewModel, String>("Nom");
        firstNameCol.setCellValueFactory(ArticleUtil.callbackName());
        TableColumn<ArticleViewModel, String> emailCol = new TableColumn<ArticleViewModel, String>("Date");
        emailCol.setCellValueFactory(ArticleUtil.callbackName());
        TableColumn<ArticleViewModel, EventHandler<ActionEvent>> buttonCol = new TableColumn<ArticleViewModel, EventHandler<ActionEvent>>();
        buttonCol.setCellValueFactory(ArticleUtil.callbackButton());       
        
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getColumns().add(firstNameCol);
        table.getColumns().add(emailCol);
        table.getColumns().add(buttonCol);
        
        vbox.getChildren().addAll(hBox, table, buttonCreate);
        VBox.setVgrow(table, Priority.ALWAYS);

        buttonSearch.onActionProperty().bindBidirectional(searchViewModel.onSearch());
        textField.textProperty().bindBidirectional(searchViewModel.getSearchTerm());
        table.setItems(searchViewModel.getArticleViewModels());
        
    }



}
