package com.deathclaws.thediary.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.deathclaws.thediary.viewmodel.ArticleViewModel;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.StringConverter;

public final class ArticleUtil {

	private final static StringConverter<Calendar> dateToStringConverter;
	
	static {
		dateToStringConverter = new StringConverter<Calendar>() {		
			private DateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			@Override
			public String toString(Calendar object) {
				if(object == null) return "";
				return sf.format(object.getTime());
			}
			@Override
			public Calendar fromString(String string) {
				GregorianCalendar a = new GregorianCalendar();
				try {
					a.setTime(sf.parse(string));
				} catch (ParseException e) { }
				return a;						
			}
		};
	}
	
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
				Button button = new Button("View");
				button.onActionProperty().bindBidirectional(param.getValue().getActionButtonProperty());
				return new SimpleObjectProperty<Button>(button);
			}
		};
	}

	public static Callback<CellDataFeatures<ArticleViewModel, Calendar>, ObservableValue<Calendar>> callbackDate() {
		return new Callback<TableColumn.CellDataFeatures<ArticleViewModel,Calendar>, ObservableValue<Calendar>>() {
			public ObservableValue<Calendar> call(CellDataFeatures<ArticleViewModel, Calendar> param) {
				return param.getValue().getDate(); 
			}
		};
	}

	public static Callback<TableColumn<ArticleViewModel, Calendar>, TableCell<ArticleViewModel, Calendar>> callbackDate2() {
		return new Callback<TableColumn<ArticleViewModel,Calendar>, TableCell<ArticleViewModel,Calendar>>() {
			public TableCell<ArticleViewModel, Calendar> call(TableColumn<ArticleViewModel, Calendar> param) {
				return new TextFieldTableCell<ArticleViewModel, Calendar>(dateToStringConverter);
			}
		};
	}


}
