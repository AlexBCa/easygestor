package es.alex.easygestor;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.Libro;
import model.Usuario;

public class AddLibroController implements Initializable {
	
    @FXML
    private TextField textTitulo;

    @FXML
    private TextField textAutor;

    @FXML
    private TextField textEditorial;

    @FXML
    private TextField textEdicion;

    @FXML
    private Button botonSave;

    @FXML
    private Button botonExit;

    @FXML
    private TextField textIsbn;
    
    private Libro libro;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	public void initData(Libro selectedItem) {
		// TODO Auto-generated method stub
		this.libro = selectedItem;
		textTitulo.setText(libro.getTitulo());
		textAutor.setText(libro.getAutores());
		textEditorial.setText(libro.getEditorial());
		textEdicion.setText(libro.getEdicion());
		textIsbn.setText(Integer.toString(libro.getIsbn()));
		
		
		
	}
	
	
	public Libro getNewLibro() {
		Libro newLibro = null;
		try {
			
			newLibro = new Libro();
			newLibro.setTitulo(textTitulo.getText());
			newLibro.setAutores(textAutor.getText());
			newLibro.setEditorial(textEditorial.getText());
			newLibro.setEdicion(textEdicion.getText());
			newLibro.setIsbn(Integer.parseInt(textIsbn.getText()));

			
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			alerta("Tipo de dato erroneo",AlertType.ERROR);
		}
		
		
		return newLibro;
		
	}
	
	/**
	 * Lanza una alerta al usuario.
	 * @param nombre
	 * @param alert
	 */
	public void alerta(String nombre, Alert.AlertType alert) {
		Alert alerta = new Alert(alert, nombre);
		
		alerta.showAndWait();
	}

}
