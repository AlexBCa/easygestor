package es.alex.easygestor;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Crud;
import model.Usuario;

public class UsuarioController extends PanelPadre implements Initializable {
	

    @FXML
    private TableView<Usuario> tabla_user;

    @FXML
    private TableColumn<Usuario, String> columSocio;

    @FXML
    private TableColumn<Usuario, String> columDni;

    @FXML
    private TableColumn<Usuario, String> columNombre;

    @FXML
    private TableColumn<Usuario, String> columApellidos;

    @FXML
    private Button add_user;

    @FXML
    private Button edit_user;

    @FXML
    private Button del_user;

    @FXML
    private TextField buscar_user;
    
    public Crud manager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("cambio de pantalla realizado");
		edit_user.setDisable(true);
		del_user.setDisable(true);
		manager = new Crud();
		manager.setup();
		
		
		cargarTabla();
		botonEdit(resources);
		cambios();
		buscar_user.setPromptText("Buscar Apellidos");
		
		add_user.setOnAction(this::botonAdd);
		del_user.setOnAction(this::botonDel);
		
		
		
		
		
		
		tabla_user.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener<Usuario>() {
			@Override
			public void changed(ObservableValue<? extends Usuario> observable, Usuario oldValue, Usuario newValue) {
				if (tabla_user.getSelectionModel().getSelectedItem() != null) {
					edit_user.setDisable(false);
					del_user.setDisable(false);
				} else {
					edit_user.setDisable(true);
					del_user.setDisable(true);
				}
			}
		});
		
	
		
	}
	
	/**
	 * Carga los usuarios en la base de datos y los inserta en la tabla.
	 */
	public void cargarTabla() {
			
			tabla_user.getItems().clear();
			List<Usuario> usuarios = manager.findAllUsuarios();
			//Creamos un objeto Observable donde se guardarán todos los objetos usuarios.
			ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList(usuarios);
			
			
			//se envia a la celda el parametro a mostar
			columSocio.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Nsocio"));
			columDni.setCellValueFactory(new PropertyValueFactory<Usuario, String>("dni"));
			columNombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
			columApellidos.setCellValueFactory(new PropertyValueFactory<Usuario, String>("apellidos"));
			
			
			//Añadimos los Usuarios a la tabla.
			tabla_user.setItems(listaUsuarios);
			
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
	 * Devuelve el objeto usuairo selecionado en la lista.
	 * @return
	 */
	public Object obtenerObjetoFoco() {
		ObservableList<?> listaUser;
		
		listaUser = tabla_user.getSelectionModel().getSelectedItems();
		
		if(listaUser.isEmpty()) {
			alerta("Selecione una fila para borrar", AlertType.ERROR);
			return 0;
		}
		return listaUser.get(0);
	}
	
	/**
	 * Lanza una nueva ventana en la que se puede introducir los campos de usuario.
	 * @param event
	 */
	
	public void botonAdd(ActionEvent event) {
		try {
			// Cargamos la informacion de la ventana de edicion que abriremos
			FXMLLoader loader = new FXMLLoader(App.class.getResource("ui_usuario_add.fxml"));
			Stage stage = new Stage();
			stage.setTitle("Editar registro");
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
	
	/**
	 * Lanza una nueva ventana con los atributos del objeto selecionado.
	 * @param resources
	 */
	public void botonEdit(ResourceBundle resources) {
		edit_user.setOnMouseClicked(event -> {
			try {
				// Cargamos la informacion de la ventana de edicion que abriremos
				FXMLLoader loader = new FXMLLoader(App.class.getResource("ui_usuario_add.fxml"), resources);
				Stage stage = new Stage();
				stage.setTitle("Editar registro");
				Parent root = loader.load();
				stage.setScene(new Scene(root));
				// Añadimos la fila seleccionada al controlador para poder editarlo
				((AddUsuarioController) loader.getController()).initData(tabla_user.getSelectionModel().getSelectedItem());
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
	
	public void botonDel(ActionEvent event) {
		
		
		Usuario user = (Usuario) obtenerObjetoFoco();
		if(user != null) {
			try {
				manager.delete(user);
				alerta("¿Seguro que quieres borrar el usuario?", AlertType.CONFIRMATION);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				alerta("Error al borrar usuario.", AlertType.ERROR);
			}
		}
		
		cargarTabla();
		
		
	}
	
	// Buscar
	/**
	 * Detecta cada cambio en el TextField y lanza un evento.
	 */
	public void cambios() {
		
		buscar_user.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				System.out.println("Cambios");
				cargarTablaPersonalizada(buscar_user.getText().toString());
			}
			
		});
		
	}
	
	/**
	 * Actualiza la tabla con los usuarios que coincidan con el texto.
	 * @param apellido
	 */
	public void cargarTablaPersonalizada(String apellido) {
		tabla_user.getItems().clear();
		List<Usuario> usuarios = manager.searchAllUsuarios(apellido);
		//Creamos un objeto Observable donde se guardarán todos los objetos usuarios.
		ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList(usuarios);
		
		
		//se envia a la celda el parametro a mostar
		columSocio.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Nsocio"));
		columDni.setCellValueFactory(new PropertyValueFactory<Usuario, String>("dni"));
		columNombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
		columApellidos.setCellValueFactory(new PropertyValueFactory<Usuario, String>("apellidos"));
		
		
		//Añadimos los Usuarios a la tabla.
		tabla_user.setItems(listaUsuarios);
		
	}
	

}
