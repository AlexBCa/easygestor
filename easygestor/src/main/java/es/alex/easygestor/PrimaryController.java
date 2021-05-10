package es.alex.easygestor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.mail.MessagingException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import model.ConexionJson;
import model.Crud;
import model.Libro;
import model.Prestamo;
import model.Usuario;

/**
 * Esta clase controla los eventos lanzados al pulsar botones en la interfaz.
 * @author Alex
 *
 */

public class PrimaryController implements Initializable {
	
    @FXML
    private BorderPane panelPrincipal;

    
    @FXML
    void panelUsuario(MouseEvent event) {
    	
    	loadUI("ui_usuarios");

    }
    
    @FXML
    void panelBiblioteca(MouseEvent event) {
    	loadUI("ui_libros");

    }

    @FXML
    void panelConfig(MouseEvent event) {
    	loadUI("ui_config");

    }

    @FXML
    void panelPrestamo(MouseEvent event) {
    	loadUI("ui_prestamo");

    }
    
    Crud manager;

    



    /**
     * Metodo para cargar las distintas pantallas.
     * @param ui. Este parametro permitira escoger las distintas interfaces.
     */
    private void loadUI(String ui) {
    	Parent root = null;
    	try {
			root = FXMLLoader.load(getClass().getResource(ui+ ".fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	panelPrincipal.setCenter(root);
    }

    /**
     * Este metodo se ejecuta al iniciar la interfaz. 
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		
		loadUI("ui_usuarios");
		
		iniciarConfigJson();
		
		
		
	
		

		
		
		
		
		
	}

	private void iniciarConfigJson() {
		//comprobar si existe el archivo de json y iniciarlo
		try {
			File fileJson = new File("config.json");
			ConexionJson manageJson = new ConexionJson();
			
			if(!(fileJson.exists())) {
				manageJson.createJson();
				alerta("Recuerde que puede configurar el envio automatico de Email ",
						Alert.AlertType.INFORMATION);
			}
			else if(!(manageJson.checkConfig())) {
				alerta("Recuerde que puede configurar el envio automatico de Email ",
						Alert.AlertType.INFORMATION);
			}
			else {
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			alerta("Error al cargar la configuración",
					Alert.AlertType.ERROR);
		}
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
	

	
	

}
