package es.alex.easygestor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.mail.MessagingException;
import javax.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;

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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.ConexionJson;
import model.Crud;
import model.Prestamo;
import model.Usuario;
import security.Aes256;

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
    public SendEmail managerEmail;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		System.out.println("cambio de pantalla realizado");
		edit_user.setDisable(true);
		del_user.setDisable(true);
		manager = new Crud();
		manager.setup();
		
		managerEmail = new SendEmail();
		
		
		
		cargarTabla();
		botonEdit(resources);
		detectarEscritura();
		detectSelect();
		buscar_user.setPromptText("Buscar Apellidos");
		
		
		
		
		add_user.setOnAction(this::botonAdd);
		del_user.setOnAction(this::botonDel);
		
		
		sendEmailMulta();
		
		
		
		ConexionJson json = new ConexionJson();
		try {
			//json.setFecha();
			System.out.println(manager.comprobrarMultaUsuario(1));
			System.out.println(json.compareFechas());
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		
		Aes256 aes = new Aes256();
		
		try {
			String texto = aes.cifrar("contrase??a");
			System.out.println(texto);
			System.out.println(aes.descrifrar("l/Pf0T9L2IhmmaJNXngjIQ=="));
			
			
			ConexionJson json = new ConexionJson();
			//json.getUserAndPass();
			
			
			
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
*/
	
		
	}
	
	/**
	 * Carga los usuarios en la base de datos y los inserta en la tabla.
	 */
	public void cargarTabla() {
			
			tabla_user.getItems().clear();
			List<Usuario> usuarios = manager.findAllUsuarios();
			//Creamos un objeto Observable donde se guardar??n todos los objetos usuarios.
			ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList(usuarios);
			
			
			//se envia a la celda el parametro a mostar
			columSocio.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Nsocio"));
			columDni.setCellValueFactory(new PropertyValueFactory<Usuario, String>("dni"));
			columNombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
			columApellidos.setCellValueFactory(new PropertyValueFactory<Usuario, String>("apellidos"));
			
			
			//A??adimos los Usuarios a la tabla.
			tabla_user.setItems(listaUsuarios);
			
			
			
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
			stage.setResizable(false);
			stage.setTitle("A??adir Usuario");
			Parent root = loader.load();
			// Importantisimo la ruta no puede empezar por /
	        stage.getIcons().add(new Image(App.class.getResourceAsStream("image/ico.png")));
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
				stage.setResizable(false);
				stage.setTitle("Editar usuario");
				Parent root = loader.load();
				// Importantisimo la ruta no puede empezar por /
		        stage.getIcons().add(new Image(App.class.getResourceAsStream("image/ico.png")));
				
				stage.setScene(new Scene(root));
				
				// A??adimos la fila seleccionada al controlador para poder editarlo
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
	
	public void botonDel(ActionEvent event) throws HibernateException {
		
		
		Usuario user = (Usuario) obtenerObjetoFoco();
		if(user != null) {
			try {
				
				manager.delete(user);
				alerta("Usuario borrado", AlertType.INFORMATION);
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				alerta("No puedes borrar un usuario que tiene un prestamo", AlertType.ERROR);
				
			}
			catch(Exception e1) {
				alerta("Error desconocido al borrar usuario.", AlertType.ERROR);
				e1.printStackTrace();
			}
		}
		
		cargarTabla();
		
		
	}
	
	// Buscar
	/**
	 * Detecta cada cambio en el TextField y lanza un evento.
	 */
	public void detectarEscritura() {
		
		buscar_user.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				//System.out.println("Cambios");
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
		//Creamos un objeto Observable donde se guardar??n todos los objetos usuarios.
		ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList(usuarios);
		
		
		//se envia a la celda el parametro a mostar
		columSocio.setCellValueFactory(new PropertyValueFactory<Usuario, String>("Nsocio"));
		columDni.setCellValueFactory(new PropertyValueFactory<Usuario, String>("dni"));
		columNombre.setCellValueFactory(new PropertyValueFactory<Usuario, String>("nombre"));
		columApellidos.setCellValueFactory(new PropertyValueFactory<Usuario, String>("apellidos"));
		
		
		//A??adimos los Usuarios a la tabla.
		tabla_user.setItems(listaUsuarios);
		
	}
	
	/**
	 * Detecta cuando se hace clic en uno de los elementos de la lista y lanza un evento que activa los botones edita y borrar.
	 */
	public void detectSelect() {
		
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
	 * Envia un correo a todos los usuarios que tengan un retraso en la entrega y cambia lo marca como multado.
	 */
	public void sendEmailMulta() {
		
		try {
			ConexionJson json = new ConexionJson();
			if(json.checkConfig()&& json.compareFechas()) {
				
				List<Prestamo> getPrestamos = manager.getPrestamosSinMultar();
				if(!(getPrestamos.isEmpty())) {
					for(Prestamo prestamoMultar: getPrestamos ) {
						
						Usuario usuarioMultar = manager.readUsuario(prestamoMultar.getNsocio());
						prestamoMultar.setMultado(true);
						managerEmail.setTO(usuarioMultar.getEmail());
			
						setUserAndPassToEmail();
						
						managerEmail.send();
						alerta("Email enviado al usuario  " + usuarioMultar.getNombre() + " " +usuarioMultar.getApellidos(),  AlertType.INFORMATION);
						
						manager.update(prestamoMultar);
					}
				}
				
			}
			// Cambiamos la fecha por la actual para denotar que hoy ay se hizo la busqueda.
			json.setFecha();
			

		}
		catch(Exception e) {
			e.printStackTrace();
			alerta("Contrase??a equivocada", Alert.AlertType.ERROR);
			
		}
		
		
		
	}
	/**
	 * Cogemos el usuario y contrase??a del json y se la pasabamos la configuramos para el gmail
	 * @throws IOException
	 * @throws NoSuchPaddingException 
	 * @throws InvalidKeySpecException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidAlgorithmParameterException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 * @throws InvalidKeyException 
	 */
	
	public void setUserAndPassToEmail() throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
		
		ConexionJson json = new ConexionJson();
		
		String[] userAndPass = json.getUserAndPassFromJson();
		int index = userAndPass[0].indexOf("@");
		String userName = userAndPass[0].substring(0, index);

		managerEmail.setFROM(userAndPass[0]);

		managerEmail.setSMTP_PASSWORD(userAndPass[1]);
		
		managerEmail.setFROMNAME(userName);
		managerEmail.setSMTP_USERNAME(userName);
		
		
	}
	
	
	

}
