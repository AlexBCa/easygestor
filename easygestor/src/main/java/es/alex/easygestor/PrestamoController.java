package es.alex.easygestor;

import java.io.Serializable;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javassist.tools.Callback;
import model.Crud;
import model.Libro;

import model.Prestamo;
import model.Usuario;
import model.Prestamo;

public class PrestamoController implements Initializable {
	
    @FXML
    private TextField textIsbn;

    @FXML
    private TextField textNsocio;

    @FXML
    private Text outTitulo;

    @FXML
    private Text outNombre;

    @FXML
    private TableView<Prestamo> tablaPrestamos;

    @FXML
    private TableColumn<Prestamo, String> columId;

    @FXML
    private TableColumn<Prestamo, String> columNsocio;

    @FXML
    private TableColumn<Prestamo, String> columIsbn;

    @FXML
    private TableColumn<Prestamo, String> columFechaPrestamo;

    @FXML
    private TableColumn<Prestamo, String> columFechaEntrega;

    @FXML
    private Button botonPrestar;

    @FXML
    private Button botonDevolver;

    @FXML
    private TextField searchTitulo;
    
    public Crud manager;
    
    public boolean usuarioValido;
    
    public boolean libroValido;
    

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		botonDevolver.setDisable(true);
		
		manager = new Crud();
		manager.setup();
		
		cargarTabla();
		detectSelect();
		detectarEstrituraLibro(textIsbn);
		detectarEstrituraUsuario(textNsocio);
		detectarPulsaciones();
		
		//System.out.println(manager.contarPrestamos(1));
		
		
		botonPrestar.setOnAction(this::prestar);
		botonDevolver.setOnAction(this::borrar);
		
		//System.out.println(manager.relacionUsuario(843700).getTitulo());
		
		//System.out.println(manager.getPrestamoPorTitulos("refor").get(0).getIsbn());
		searchTitulo.setPromptText("Buscar por título");
		textIsbn.setPromptText("6 primeros digitos");
		textNsocio.setPromptText("N socio");
		
		
		
	}
	
	
	/**
	 * Carga los usuarios en la base de datos y los inserta en la tabla.
	 */

	public void cargarTabla() {
			
			tablaPrestamos.getItems().clear();
			
			//List<Prestamo> prestamos = manager.findAllPrestamos();
			List<Prestamo> prestamos = manager.findAllPrestamos();

			
			//Creamos un objeto Observable donde se guardarán todos los objetos usuarios.
			ObservableList<Prestamo> listaPrestamos = FXCollections.observableArrayList(prestamos);
			
			//idea como añadir el titulo relacionado.
			
			//se envia a la celda el parametro a mostar
			//columId.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("id_prestamo"));
			columId.setCellValueFactory(data -> {
				
				Libro lb = manager.getLibroPrestado(data.getValue().getIsbn());
				
				
				
				return new ReadOnlyStringWrapper(lb.getTitulo());
			});
			columNsocio.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("Nsocio"));
			columIsbn.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("isbn"));
			columFechaPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("fecha_prestamo"));
			columFechaEntrega.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("fecha_limite_prestamo"));
			
			
			//Añadimos los Usuarios a la tabla.
			tablaPrestamos.setItems(listaPrestamos);
			
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
	 * Detecta cuando se hace clic en uno de los elementos de la lista y lanza un evento que activa los botones edita y borrar.
	 */
	public void detectSelect() {
		
		tablaPrestamos.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener<Prestamo>() {
			@Override
			public void changed(ObservableValue<? extends Prestamo> observable, Prestamo oldValue, Prestamo newValue) {
				if (tablaPrestamos.getSelectionModel().getSelectedItem() != null) {
					
					botonDevolver.setDisable(false);
				} else {
					
					botonDevolver.setDisable(true);
				}
			}
		});
	}
	
	public void detectarEstrituraLibro(TextField cuadro) {
		
		cuadro.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				System.out.println("Cambios");
				Libro lib = null;
				try {
					if((textIsbn.getText().isEmpty())) {
						
						outTitulo.setText("Muestra titulo libro");
						
					}
					else {
						lib = buscarLibro(Integer.parseInt(textIsbn.getText()));
					}
					
					if(lib != null && !(observable.getValue().isEmpty())) {
						outTitulo.setText(lib.getTitulo());
						System.out.println(observable.getValue());
						// Valida el libro
						libroValido = true;
						
					}else if (textIsbn.getText().length() > 0){
						outTitulo.setText("Desconocido");
					}
					
				}
				catch(NumberFormatException e) {
					outTitulo.setText("Desconocido");
				}

			}
			
		});
		
	}
	
	public Libro buscarLibro(int isbn) {
		Libro libro = new Libro();
		libro = manager.readLibro(isbn);
		return libro;
		
	}
	
	public void detectarEstrituraUsuario(TextField cuadro) {
			
			cuadro.textProperty().addListener(new ChangeListener<String>() {
	
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					// TODO Auto-generated method stub
					System.out.println("Cambios");
					Usuario user = null;
					try {
						if((textNsocio.getText().isEmpty())) {
							
							outNombre.setText("Muestra nombre socio");
							
						}
						else {
							user = buscarUsuario(Integer.parseInt(textNsocio.getText()));
						}
						
						if(user != null && !(observable.getValue().isEmpty())) {
							outNombre.setText(user.getNombre()+ " "+user.getApellidos());
							System.out.println(observable.getValue());
							// valida el usuario.
							usuarioValido = true;
							
							
						}else if (textNsocio.getText().length() > 0){
							outNombre.setText("Desconocido");
						}
						
					}
					catch(NumberFormatException e) {
						outNombre.setText("Desconocido");
					}
	
				}
				
			});
			
		}
		
	public Usuario buscarUsuario(int Nsocio) {
		Usuario user = new Usuario();
		user = manager.readUsuario(Nsocio);
		return user;
		
	}
	
	/**
	 * Devuelve un true si los campos de usuario y libro son correctos. 
	 * @return true o false.
	 */
	public boolean checkValidez() {
		
		if(usuarioValido && libroValido) {
			
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param event
	 */
	public void prestar(ActionEvent event) {
		
		try {
			
			int isbn = Integer.parseInt(textIsbn.getText());
			int Nsocio = Integer.parseInt(textNsocio.getText());
			
			if(checkValidez()&& checkDisponibilidad(isbn)&& !(maximoPrestAlcanzados(Nsocio))) {

				
				
				long now = System.currentTimeMillis();
		        Date dateNow= new Date(now);
		        
		        Date limite = new Date(now+1296000000);
		       
				Prestamo prestamo = new Prestamo();
				prestamo.setIsbn(isbn);
				prestamo.setNsocio(Nsocio);
		        prestamo.setFecha_prestamo(dateNow);
		        prestamo.setFecha_limite_prestamo(limite);
		        
		        // Cambiar la disponiblidad.
		        Libro lb = buscarLibro(isbn);
		        
		        lb.setDisponibilidad(false);
		        manager.create(prestamo);
		        manager.update(lb);
		       
				cargarTabla();
				
				textIsbn.setText("");
				textNsocio.setText("");
				
				
				
				
		        
		        
		        
		        
			}
			else if(checkDisponibilidad(isbn) == false) {
				alerta("El libro ya esta prestado", AlertType.ERROR);
			}
			else {
	        	alerta("Debe introducir un Nsocio y ISBN validos", AlertType.ERROR);
	        }
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			alerta("Ocurrio un error", AlertType.ERROR);
			
		}
		
		
		
	}
	
	public void borrar(ActionEvent event) {
		
		Prestamo pres = (Prestamo) obtenerObjetoFoco();
		if(pres != null) {
			try {
				
				// se consigue el libro que va a borrarse.
				Libro lb = manager.readLibro(pres.getIsbn());
				
				manager.delete(pres);
				// cambiamos la dispiniblidad.
				lb.setDisponibilidad(true);
				manager.update(lb);
				alerta("Libro borrado", AlertType.INFORMATION);
				// añadir logica para cancelar.
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				alerta("Error al devolver el libro", AlertType.ERROR);
			}
		}
		
		cargarTabla();
	}
	
	
	/**
	 * Devuelve el objeto usuairo selecionado en la lista.
	 * @return
	 */
	public Object obtenerObjetoFoco() {
		ObservableList<?> listaPrestamos;
		
		listaPrestamos = tablaPrestamos.getSelectionModel().getSelectedItems();
		
		if(listaPrestamos.isEmpty()) {
			alerta("Selecione una fila para borrar", AlertType.ERROR);
			return 0;
		}
		return listaPrestamos.get(0);
	}
	
	public boolean checkDisponibilidad(int isbn) throws Exception{
		
		Libro lb = buscarLibro(isbn);
		if(lb.getDisponibilidad()) {
			return true;
			
		}else {
			return false;
		}
		
		
	}
	
	public boolean maximoPrestAlcanzados(int nsocio) {
		
		if(manager.contarPrestamos(nsocio)> 3) {
			
			alerta("No se puede tener más de 3 libros en prestamos.", AlertType.ERROR);
			
			return true;
			
		}
		else {
			return false;
		}
	}
	
	public List<Prestamo> getPrestamosIncumplidos(){
		
		List<Prestamo> fechas = manager.findAllPrestamos();
		
		List<Prestamo> pasadas = new ArrayList<Prestamo>();
		
		for(Prestamo fecha: fechas) {
			
			long now = System.currentTimeMillis();
	        Date dateNow= new Date(now);
			Date fechaLimite = fecha.getFecha_limite_prestamo();
			
			if(dateNow.after(fechaLimite)) {
				pasadas.add(fecha);
			}
			
			
		}
		return null;
	}
	
	public void detectarPulsaciones() {
		
		searchTitulo.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				System.out.println("Cambios");
				cargarTablaBusqueda(searchTitulo.getText().toString());
				
			}
			
		});
		
	}
	
	/**
	 * Carga la tabla y muestra los perstamos que coinciden con el titulo.
	 * @param titulo
	 */
	public void cargarTablaBusqueda(String titulo) {
		
		tablaPrestamos.getItems().clear();
		
		//List<Prestamo> prestamos = manager.findAllPrestamos();
		List<Prestamo> prestamos = manager.getPrestamoPorTitulos(titulo);

		
		//Creamos un objeto Observable donde se guardarán todos los objetos usuarios.
		ObservableList<Prestamo> listaPrestamos = FXCollections.observableArrayList(prestamos);
		
		//idea como añadir el titulo relacionado.
		
		//se envia a la celda el parametro a mostar
		//columId.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("id_prestamo"));
		columId.setCellValueFactory(data -> {
			
			Libro lb = manager.getLibroPrestado(data.getValue().getIsbn());
			
			
			
			return new ReadOnlyStringWrapper(lb.getTitulo());
		});
		columNsocio.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("Nsocio"));
		columIsbn.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("isbn"));
		columFechaPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("fecha_prestamo"));
		columFechaEntrega.setCellValueFactory(new PropertyValueFactory<Prestamo, String>("fecha_limite_prestamo"));
		
		
		//Añadimos los Usuarios a la tabla.
		tablaPrestamos.setItems(listaPrestamos);
		
	}
	

	
	
	
	
}


