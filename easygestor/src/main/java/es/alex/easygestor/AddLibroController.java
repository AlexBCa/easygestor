package es.alex.easygestor;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import model.Crud;
import model.Libro;
import model.Usuario;

public class AddLibroController implements Initializable {
	
    @FXML
    private TextField textTitulo;

    @FXML
    private TextField textAutor;

    @FXML
    private TextField textEditorial;

    @FXML
    private TextField textEdicion;

    @FXML
    private Button botonSave;

    @FXML
    private Button botonExit;

    @FXML
    private TextField textIsbn;
    
    private Libro libro;
    
    private boolean modeEdit;
    
    private Crud manageCrud;
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		manageCrud = new Crud();
		manageCrud.setup();
		
		
		
		
		botonSave.setOnAction(this::selectMode);
		botonExit.setOnAction(this::close);
		
		
		
	}

	public void initData(Libro selectedItem) {
		// TODO Auto-generated method stub
		this.libro = selectedItem;
		textTitulo.setText(libro.getTitulo());
		textAutor.setText(libro.getAutores());
		textEditorial.setText(libro.getEditorial());
		textEdicion.setText(libro.getEdicion());
		textIsbn.setText(Integer.toString(libro.getIsbn()));
		
		getMode();
		if(modeEdit) {
			System.out.println("soy true");
			textIsbn.setDisable(true);
			
		}else {
			System.out.println("soy false");
			textIsbn.setVisible(true);
		}
		
	}
	
	
	public Libro getNewLibro() {
		Libro newLibro = null;
		try {
			
			newLibro = new Libro();
			newLibro.setTitulo(textTitulo.getText());
			newLibro.setAutores(textAutor.getText());
			newLibro.setEditorial(textEditorial.getText());
			newLibro.setEdicion(textEdicion.getText());
			newLibro.setIsbn(Integer.parseInt(textIsbn.getText()));
			newLibro.setDisponibilidad(true);

			
		}
		catch(NumberFormatException e) {
			e.printStackTrace();
			alerta("Tipo de dato erroneo",AlertType.ERROR);
		}
		
		
		return newLibro;
		
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
	 * Comprueba si todo los campos han sido rellenados.
	 * @return Valido. Es un boleano que devuelve true o false.
	 */
	public Boolean checkCampos() {
		Boolean valido = false;
	
		try {
			if(textTitulo.getText().isEmpty()|| textAutor.getText().isEmpty()|| textEditorial.getText().isEmpty()||
					textEdicion.getText().isEmpty()|| textIsbn.getText().isEmpty()) {
				
				
				valido = false;
				throw new Exception();
				
			}else if(textIsbn.getText().length()>6) {
				alerta("Escribe solo los 6 primeros números", AlertType.ERROR);
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
	 * Sirve para diferenciar si es una nueva entrada o una edición.
	 */
	public void getMode() {
		
		if(textTitulo.getText().isEmpty()) {
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
	 * Cierra una ventana.
	 */
	public void close(ActionEvent event) {
		Window ventana = botonExit.getScene().getWindow();
		ventana.fireEvent(new WindowEvent(ventana, WindowEvent.WINDOW_CLOSE_REQUEST));
	    // do what you have to do
	    
	}
	
	/**
	 * Guarda las nuevos datos y crea un registro.
	 * @param event
	 */
	public void save(ActionEvent event) {
		
		if(checkCampos()) {
			Libro newLibro = getNewLibro();
			
			

			try {
				manageCrud.create(newLibro);
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
			Libro editLibro = getNewLibro();
			
			try {
				System.out.println("Actualizando");
				manageCrud.update(editLibro);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				alerta("Ocurrio un error al editar el libro",AlertType.ERROR);
			}
			
			
		}
		close(event);
		
	}
	
	
}
