package es.alex.easygestor;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.Crud;
import model.Libro;
import model.Prestamo;
import model.Usuario;
import model.Prestamo;

public class PrestamoController implements Initializable {
	
    @FXML
    private TextField textIsbn;

    @FXML
    private TextField textNsocio;

    @FXML
    private Text outTitulo;

    @FXML
    private Text outNombre;

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
		detectSelect();
		detectarEstrituraLibro(textIsbn);
		
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
	
	/**
	 * Lanza una alerta al usuario.
	 * @param nombre. Texto de la alerta.
	 * @param alert. Tipo de la alerta.
	 */
	public void alerta(String nombre, Alert.AlertType alert) {
		Alert alerta = new Alert(alert, nombre);
		
		alerta.showAndWait();
	}
	
	/**
	 * Detecta cuando se hace clic en uno de los elementos de la lista y lanza un evento que activa los botones edita y borrar.
	 */
	public void detectSelect() {
		
		tablaPrestamos.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener<Prestamo>() {
			@Override
			public void changed(ObservableValue<? extends Prestamo> observable, Prestamo oldValue, Prestamo newValue) {
				if (tablaPrestamos.getSelectionModel().getSelectedItem() != null) {
					botonPrestar.setDisable(false);
					botonDevolver.setDisable(false);
				} else {
					botonPrestar.setDisable(true);
					botonDevolver.setDisable(true);
				}
			}
		});
	}
	
	public void detectarEstrituraLibro(TextField cuadro) {
		
		cuadro.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				System.out.println("Cambios");
				Libro lib = null;
				try {
					if((textIsbn.getText().isEmpty())) {
						
						outTitulo.setText("Muestra titulo libro");
						
					}
					else {
						lib = buscarLibro(Integer.parseInt(textIsbn.getText()));
					}
					
					if(lib != null && !(observable.getValue().isEmpty())) {
						outTitulo.setText(lib.getTitulo());
						System.out.println(observable.getValue());
						
					}else if (textIsbn.getText().length() > 0){
						outTitulo.setText("Desconocido");
					}
					
				}
				catch(NumberFormatException e) {
					outTitulo.setText("Desconocido");
				}

			}
			
		});
		
	}
	
	public Libro buscarLibro(int isbn) {
		Libro libro = new Libro();
		libro = manager.readLibro(isbn);
		return libro;
		
	}
}
