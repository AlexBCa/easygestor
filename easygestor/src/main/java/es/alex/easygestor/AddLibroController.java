package es.alex.easygestor;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.Libro;

public class AddLibroController implements Initializable {
	
    @FXML
    private TextField textName;

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}

	public void initData(Libro selectedItem) {
		// TODO Auto-generated method stub
		
	}

}
