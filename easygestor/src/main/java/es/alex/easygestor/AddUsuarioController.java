package es.alex.easygestor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.PersistenceException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.Crud;
import model.Usuario;
import org.apache.commons.validator.routines.EmailValidator;

public class AddUsuarioController extends PanelPadre implements Initializable{
	
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
    
    @FXML
    private AnchorPane panel;
	
	private Usuario user;
	
	private Crud manageCrud;
	
	public boolean modeEdit;
	
	private final int limitLenghtTelefono = 9;

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
		
		//Stage stage = (Stage) textDni.getScene().getWindow();
		
		
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
				
			}else if(!(EmailValidator.getInstance().isValid(textEmail.getText()))) {
				valido = false;
				alerta("Email no valido", AlertType.ERROR);
				
			}
			else if(!(validarDni(textDni.getText()))) {
				alerta("El DNI no tiene el formato correcto", AlertType.ERROR);
				
			}
			else if(textTele.getText().length()<9 || textTele.getText().length()>14){
				alerta("El numero de tel??fono no valido", AlertType.ERROR);
			}
			else {
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
			
			
			}
			
			catch(PersistenceException p) {
				alerta("DNI repetido",AlertType.ERROR);
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				alerta("Ocurrio un error al crear el libro",AlertType.ERROR);
			}
					

			
			
			close(event);
			
		}
		
		
		

		
	}
	/**
	 * Esta funci??n actauliza un registro de la tabla.
	 * @param event
	 */
	public void saveEdit(ActionEvent event) {
		if(checkCampos()) {
			Usuario newUser = newUser();
			newUser.setNsocio(user.getNsocio());
			try {
				manageCrud.update(newUser);
			} catch(PersistenceException p) {
				alerta("DNI repetido",AlertType.ERROR);
			}
			
			
			catch (Exception e) {
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
	 * Sirve para diferenciar si es una nueva entrada o una edici??n.
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
	 * Comprueba si esta en modo editar o a??adir y lanza los metodos correspondiente. 
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
	 * Cierra una ventana.
	 */
	public void close(ActionEvent event) {
		Window ventana = botonExit.getScene().getWindow();
		ventana.fireEvent(new WindowEvent(ventana, WindowEvent.WINDOW_CLOSE_REQUEST));
	    // do what you have to do
	    
	}
	
	/**
	 * Comprueba que el dni tenga un formato correcto.
	 * @param dni
	 * @return
	 */
	public boolean validarDni(String dni) {
		//Copila las expresiones regular
		// la expresi??n regular dicta que tenga que tener 8 numeros y una cadena.
		Pattern pat = Pattern.compile("[0-9]{7,8}[A-Z a-z]");
		// Comprobamos el dni con el patr??n pasadado como expresi??n regular.
		Matcher mat = pat.matcher(dni);
		
		return mat.matches();
	}
	

	

}
