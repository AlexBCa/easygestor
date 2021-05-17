package es.alex.easygestor;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Crud;
import model.Usuario;

public class PanelPadre {
	
	public void alerta(String nombre, Alert.AlertType alert) {
		Alert alerta = new Alert(alert, nombre);
		DialogPane dialogPane = alerta.getDialogPane();
		dialogPane.getStylesheets().add(
		   getClass().getResource("DarkThemeAlert.css").toExternalForm());
		dialogPane.getStyleClass().add("myDialog");
		
		Stage stage = (Stage) dialogPane.getScene().getWindow();
		stage.getIcons().add(new Image(App.class.getResourceAsStream("image/ico.png")));
		
		
		
		alerta.showAndWait();
	}
	
	
	
	

	}


