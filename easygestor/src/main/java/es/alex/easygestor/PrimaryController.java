package es.alex.easygestor;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.mail.MessagingException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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
		
		
		ConexionJson js = new ConexionJson();
		try {
			SendEmail enviarEmail = new SendEmail("estaneurona@gmail.com");
			enviarEmail.send();
			
		} catch (IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		
		System.out.println("Hola");
		
		Usuario user = new Usuario();
		user.setNsocio(2541615);
		user.setDni("55d56d");
		user.setNombre("Ana");
		user.setApellidos("Jimenez");
		user.setDireccion("calle falsa 123");
		user.setEmail("email@falso.com");
		user.setTelefono(955555);
		
		Usuario useralt = new Usuario();
		useralt.setNsocio(2541615);
		useralt.setDni("55d56d");
		useralt.setNombre("Ana");
		useralt.setApellidos("Jimenez Rodriguez");
		useralt.setDireccion("calle falsa 123");
		useralt.setEmail("email@falso.com");
		useralt.setTelefono(955555);
		
		Libro l = new Libro();
		l.setIsbn(585855);
		l.setAutores("Dawking");
		l.setEdicion("primera");
		l.setEditorial("ni idea");
		l.setTitulo("El gen egoista");
		l.setDisponibilidad(true);
		
		Prestamo pr = new Prestamo();
		pr.setIsbn(585855);
		pr.setNsocio(2541615);
		
		long now = System.currentTimeMillis();
        Date sqlDate = new Date(now);
        
        // Sumamos 15 dias en ms
        Date limite = new Date(now+1296000000);
        
		pr.setFecha_prestamo(sqlDate);
		pr.setFecha_limite_prestamo(limite);
		
		
		Crud c = new Crud();
		
	
		
		*/
		
		
		
		
		
	}
}
