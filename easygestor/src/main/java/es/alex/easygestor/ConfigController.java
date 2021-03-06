package es.alex.easygestor;

import java.io.IOException;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.validator.routines.EmailValidator;

import com.google.protobuf.DescriptorProtos.FieldDescriptorProto.Label;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import model.ConexionJson;
import security.Aes256;

public class ConfigController extends PanelPadre implements Initializable{
	
	@FXML
    private TextField campoEmail;

    @FXML
    private Button botonSave;

    @FXML
    private PasswordField campoPassword;
    
    @FXML
    private Text mostrarConfigurado;

    
    ConexionJson manageJson;
    
    SendEmail manageEmail;
    




	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		manageJson = new ConexionJson();
		manageEmail = new SendEmail();
		
		try {
			if(manageJson.checkConfig()) {
				mostrarConfigurado.setText("¬°Configurado!");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		
		//configurar json
		botonSave.setOnAction(this::saveUserAndPass);
		
		
		
		
		
		
	}
	
	/**
	 * Recoges los parametros de los campos y los guarda en un json.
	 * @param e
	 */
	public void saveUserAndPass(ActionEvent e) {
		
		
		
		try {
			String userEmail = campoEmail.getText();
			String userPassword = campoPassword.getText();
			
			
			
			
			if(checkCampos()) {
				
				manageJson.addInfoJson(userEmail, userPassword);
				alerta("Datos guardados", Alert.AlertType.INFORMATION);
				
			}
		} catch (IOException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException  e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			alerta("Error al escribir el archivo",Alert.AlertType.ERROR);
		} finally{
			campoEmail.setText("");
			campoPassword.setText("");
			
		}
	}
	
	/**
	 * Comprueba si todo los campos han sido rellenados.
	 * @return Valido. Es un boleano que devuelve true o false.
	 */
	public Boolean checkCampos() {
		Boolean valido = false;
	
		try {
			if(campoEmail.getText().isEmpty()|| campoPassword.getText().isEmpty()) {
				
				
				valido = false;
				throw new Exception();
				
			}
			else if(!(EmailValidator.getInstance().isValid(campoEmail.getText()))) {
				alerta("Formato de Email no valido", AlertType.ERROR);
			}
			else if(!(isGmail())) {
				alerta("El correo debe ser un Gmail", AlertType.ERROR);
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
	 * identifica si el correo es un gmail.
	 * @return
	 */
	public boolean isGmail() {
		String email = campoEmail.getText();
		email = "hola@gmail.com";
		String idenfyEmail = email.split("@")[1];
		
		if(idenfyEmail.contentEquals("gmail.com")  || idenfyEmail.contentEquals("gmail.es")) {
			
			return true;
		}
		System.out.println(idenfyEmail);
		return false;
		
	}
	
	
	
	
	
	
	
	
	
	

}
