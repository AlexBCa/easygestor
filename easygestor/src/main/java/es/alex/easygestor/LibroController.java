package es.alex.easygestor;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
		detectSelect();
		cambios();
		botonEdit(resources);
		
		botonAdd.setOnAction(this::abrirAdd);
		botonDel.setOnAction(this::borrar);
		
		searchLibros.setPromptText("Buscar por título");
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
	
	/**
	 * Devuelve el objeto libro selecionado en la lista.
	 * @return
	 */
	public Object obtenerObjetoFoco() {
		ObservableList<?> listaUser;
		
		listaUser = tablaLibros.getSelectionModel().getSelectedItems();
		
		if(listaUser.isEmpty()) {
			alerta("Selecione una fila para borrar", AlertType.ERROR);
			return 0;
		}
		return listaUser.get(0);
	}
	
	/**
	 * Detecta cuando se hace clic en uno de los elementos de la lista y lanza un evento que activa los botones edita y borrar.
	 */
	public void detectSelect() {
		
		tablaLibros.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener<Libro>() {
			@Override
			public void changed(ObservableValue<? extends Libro> observable, Libro oldValue, Libro newValue) {
				if (tablaLibros.getSelectionModel().getSelectedItem() != null) {
					botonEdit.setDisable(false);
					botonDel.setDisable(false);
				} else {
					botonEdit.setDisable(true);
					botonDel.setDisable(true);
				}
			}
		});
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
	 * Actualiza la tabla con los usuarios que coincidan con el texto.
	 * @param apellido
	 */
	public void cargarTablaPersonalizada(String titulo) {
		tablaLibros.getItems().clear();
		List<Libro> libros = manager.searchAllLibros(titulo);
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
	
	public void cambios() {
			
			searchLibros.textProperty().addListener(new ChangeListener<String>() {
	
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					System.out.println("Cambios");
					cargarTablaPersonalizada(searchLibros.getText().toString());
				}
				
			});
			
		}
	
	
	/**
	 * Lanza una nueva ventana en la que se puede introducir los campos de usuario.
	 * @param event
	 */
	
	public void abrirAdd(ActionEvent event) {
		try {
			// Cargamos la informacion de la ventana de edicion que abriremos
			FXMLLoader loader = new FXMLLoader(App.class.getResource("ui_libro_add.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Editar libro");
			Parent root = loader.load();
			stage.setScene(new Scene(root));
			stage.show();
			
			// Evento que se lanzara al cerrar la ventana de edicion y actualizara los datos de la tabla
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					cargarTabla();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void borrar(ActionEvent event) {
			
			
			Libro lib = (Libro) obtenerObjetoFoco();
			if(lib != null) {
				try {
					manager.delete(lib);
					alerta("¿Seguro que quieres borrar el usuario?", AlertType.CONFIRMATION);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					alerta("Error al borrar usuario.", AlertType.ERROR);
				}
			}
			
			cargarTabla();
			
			
		}
	
	
	/**
	 * Lanza una nueva ventana con los atributos del objeto selecionado.
	 * @param resources
	 */
	public void botonEdit(ResourceBundle resources) {
		botonEdit.setOnMouseClicked(event -> {
			try {
				// Cargamos la informacion de la ventana de edicion que abriremos
				FXMLLoader loader = new FXMLLoader(App.class.getResource("ui_libro_add.fxml"), resources);
				Stage stage = new Stage();
				stage.setTitle("Editar libro");
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				// Añadimos la fila seleccionada al controlador para poder editarlo
				((AddLibroController) loader.getController()).initData(tablaLibros.getSelectionModel().getSelectedItem());
				stage.show();
				// Evento que se lanzara al cerrar la ventana de edicion y actualizara los datos de la tabla
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent event) {
						cargarTabla();
					}
				});
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	
	

}
