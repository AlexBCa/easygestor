package es.alex.easygestor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Crud;
import model.Libro;
import model.Usuario;

public class LibroController implements Initializable{
	
	@FXML
    private TableView<Libro> tablaLibros;

    @FXML
    private TableColumn<Libro, String> columIsbn;

    @FXML
    private TableColumn<Libro, String> columTitulo;

    @FXML
    private TableColumn<Libro, String> columAutor;

    @FXML
    private TableColumn<Libro, String> columEditorial;

    @FXML
    private TableColumn<Libro, String> columDispo;

    @FXML
    private Button botonAdd;

    @FXML
    private Button botonEdit;

    @FXML
    private Button botonDel;

    @FXML
    private TextField searchLibros;
    
    public Crud manager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		botonEdit.setDisable(true);
		botonDel.setDisable(true);
		manager = new Crud();
		manager.setup();
		
		cargarTabla();
	}
	
	
	/**
	 * Carga los usuarios en la base de datos y los inserta en la tabla.
	 */
	public void cargarTabla() {
			
			tablaLibros.getItems().clear();
			List<Libro> libros = manager.findAllLibros();
			//Creamos un objeto Observable donde se guardarán todos los objetos usuarios.
			ObservableList<Libro> listaLibros = FXCollections.observableArrayList(libros);
			
			
			//se envia a la celda el parametro a mostar
			columIsbn.setCellValueFactory(new PropertyValueFactory<Libro, String>("isbn"));
			columTitulo.setCellValueFactory(new PropertyValueFactory<Libro, String>("titulo"));
			columAutor.setCellValueFactory(new PropertyValueFactory<Libro, String>("autores"));
			columEditorial.setCellValueFactory(new PropertyValueFactory<Libro, String>("editorial"));
			
			//PropertyValueFactory valor = new PropertyValueFactory<Libro, String>("disponibilidad");
			
			//columDispo.setCellValueFactory(new PropertyValueFactory<Libro, String>("disponibilidad"));
			columDispo.setCellValueFactory(data -> {
				boolean valor = data.getValue().getDisponibilidad();
				String res = "";
				if(valor) {
					res = "Si";
				}
				else {
					res = "No";
				}
				
				return new ReadOnlyStringWrapper(res);
			});
			
			//Añadimos los Usuarios a la tabla.
			tablaLibros.setItems(listaLibros);
			
		}
	

}
