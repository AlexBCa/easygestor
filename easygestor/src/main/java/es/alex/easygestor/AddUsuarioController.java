package es.alex.easygestor;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.Crud;
import model.Usuario;
import org.apache.commons.validator.routines.EmailValidator;

public class AddUsuarioController implements Initializable{
	
    @FXML
    private TextField textName;

    @FXML
    private TextField textAp;

    @FXML
    private TextField textDir;

    @FXML
    private TextField textDni;

    @FXML
    private TextField textTele;

    @FXML
    private TextField textEmail;
    
    @FXML
    private Button botonSave;

    @FXML
    private Button botonExit;
	
	private Usuario user;
	
	private Crud manageCrud;
	
	public boolean modeEdit;

	public void initData(Usuario selectedItem) {
		// TODO Auto-generated method stub
		this.user = selectedItem;
		textName.setText(user.getNombre());
		textAp.setText(user.getApellidos());
		textDir.setText(user.getDireccion());
		textDni.setText(user.getDni());
		textTele.setText(Integer.toString(user.getTelefono()));
		textEmail.setText(user.getEmail());
		
		getMode();
		
		
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		manageCrud = new Crud();
		manageCrud.setup();
		
		
		botonSave.setOnAction(this::selectMode);
		botonExit.setOnAction(this::close);
		
		
		
		
		
		
	}
	/**
	 * Comprueba si todo los campos han sido rellenados.
	 * @return Valido. Es un boleano que devuelve true o false.
	 */
	public Boolean checkCampos() {
		Boolean valido = false;
	
		try {
			if(textName.getText().isEmpty()|| textAp.getText().isEmpty()|| textDir.getText().isEmpty()||
					textDni.getText().isEmpty()|| textTele.getText().isEmpty()|| textEmail.getText().isEmpty()) {
				
				
				valido = false;
				throw new Exception();
				
			}else {
				valido = true;
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			alerta("Tiene que introducir todos los campos", AlertType.ERROR);
		}
		
		
		return valido;
	}
	/**
	 * Guarda las nuevos datos y crea un registro.
	 * @param event
	 */
	public void save(ActionEvent event) {
		
		if(checkCampos()) {
			Usuario newUser = newUser();
			
			

			try {
				manageCrud.create(newUser);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				alerta("Ocurrio un error al crear el libro",AlertType.ERROR);
			}
					

			
			
			close(event);
			
		}
		
		
		

		
	}
	/**
	 * Esta función actauliza un registro de la tabla.
	 * @param event
	 */
	public void saveEdit(ActionEvent event) {
		if(checkCampos()) {
			Usuario newUser = newUser();
			newUser.setNsocio(user.getNsocio());
			try {
				manageCrud.update(newUser);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				alerta("Ocurrio un error al editar el libro",AlertType.ERROR);
			}
			
		}
		close(event);
		
	}
	
	/**
	 *  Recoge los datos introducidos por el usuario y crea un nuevo objeto.
	 * @return
	 */
	public Usuario newUser() {
		Usuario newUser = null;
		try {
			
			newUser = new Usuario();
			newUser.setNombre(textName.getText());
			newUser.setApellidos(textAp.getText());
			newUser.setDireccion(textDir.getText());
			newUser.setDni(textDni.getText());
			newUser.setTelefono(Integer.parseInt(textTele.getText()));
			newUser.setEmail(textEmail.getText());
			
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			alerta("Tipo de dato erroneo",AlertType.ERROR);
		}
		
		
		return newUser;
		
	}
	
	/**
	 * Sirve para diferenciar si es una nueva entrada o una edición.
	 */
	public void getMode() {
		
		if(textName.getText().isEmpty()) {
			modeEdit= false;
		}
		else {
			modeEdit= true;
		}
	}
	
	/**
	 * Comprueba si esta en modo editar o añadir y lanza los metodos correspondiente. 
	 * @param event
	 */
	public void selectMode(ActionEvent event){
		if(modeEdit) {
			saveEdit(event);
		}
		else {
			save(event);
		}
		
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
	
	/**
	 * Cierra una ventana.
	 */
	public void close(ActionEvent event) {
		Window ventana = botonExit.getScene().getWindow();
		ventana.fireEvent(new WindowEvent(ventana, WindowEvent.WINDOW_CLOSE_REQUEST));
	    // do what you have to do
	    
	}
	
	public boolean validarEmail(String email) {
		boolean valid = EmailValidator.getInstance().isValid(email);
		return valid;
	}
	
	

}
