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
import model.Crud;
import model.Usuario;

public class UsuarioController implements Initializable {
	

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
		
		cargarUsuariosTabla();
		
	}
	
	public void cargarUsuariosTabla() {
			
			tabla_user.getItems().clear();
			List<Usuario> usuarios = manager.findAllUsuarios();
			//Creamos una lista observable donde se guardaran todos los objetos usuarios.
			ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList(usuarios);
			
			
			//se envia a la celda el parametro a mostar
			columSocio.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Nsocio"));
			columDni.setCellValueFactory(new PropertyValueFactory<Usuario, String>("dni"));
			columNombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
			columApellidos.setCellValueFactory(new PropertyValueFactory<Usuario, String>("apellidos"));
			
			
			//Añadimos los servicos a la tabla.
			tabla_user.setItems(listaUsuarios);
			
		}

}
