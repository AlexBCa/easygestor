package es.alex.easygestor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * Esta clase controla los eventos lanzados al pulsar botones en la interfaz.
 * @author Alex
 *
 */

public class PrimaryController implements Initializable {
	
    @FXML
    private BorderPane panelPrincipal;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
    
    @FXML
    void panelUsuario(MouseEvent event) {
    	

    }
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
		
	}
}
