package es.alex.easygestor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.Crud;
import model.Prestamo;
import model.Prestamo;

public class PrestamoController implements Initializable {
	
    @FXML
    private TextField textIsbn;

    @FXML
    private TextField textNsocio;

    @FXML
    private Text out_titulo;

    @FXML
    private Text out_nombre;

    @FXML
    private TableView<Prestamo> tablaPrestamos;

    @FXML
    private TableColumn<Prestamo, String> columId;

    @FXML
    private TableColumn<Prestamo, String> columNsocio;

    @FXML
    private TableColumn<Prestamo, String> columIsbn;

    @FXML
    private TableColumn<Prestamo, String> columFechaPrestamo;

    @FXML
    private TableColumn<Prestamo, String> columFechaEntrega;

    @FXML
    private Button botonPrestar;

    @FXML
    private Button botonDevolver;

    @FXML
    private TextField searchNsocio;
    
    public Crud manager;
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		botonPrestar.setDisable(true);
		botonDevolver.setDisable(true);
		
		manager = new Crud();
		manager.setup();
		
		cargarTabla();
		
	}
	
	
	/**
	 * Carga los usuarios en la base de datos y los inserta en la tabla.
	 */
	public void cargarTabla() {
			
			tablaPrestamos.getItems().clear();
			List<Prestamo> prestamos = manager.findAllPrestamos();
			//Creamos un objeto Observable donde se guardarán todos los objetos usuarios.
			ObservableList<Prestamo> listaPrestamos = FXCollections.observableArrayList(prestamos);
			
			
			//se envia a la celda el parametro a mostar
			columId.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("id_prestamo"));
			columNsocio.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("Nsocio"));
			columIsbn.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("isbn"));
			columFechaPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("fecha_prestamo"));
			columFechaEntrega.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("fecha_limite_prestamo"));
			
			
			//Añadimos los Usuarios a la tabla.
			tablaPrestamos.setItems(listaPrestamos);
			
		}

}
