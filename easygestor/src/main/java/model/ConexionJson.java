package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.JSONObject;

public class ConexionJson {
	

	
	//guardar json en memoria.
	
	public void guardarArchivo(String json) throws IOException {
		File archivo = new File("config.json");
		
		FileWriter writer = new FileWriter(archivo);
		writer.write(json); 
	    writer.flush();
	    writer.close();
		
	}
	// leer json el archivo.
	public void leerArchivo() throws IOException {
		
		String texto = Files.readString(Path.of(System.getProperty("user.dir")+"\\config.json"));
		System.out.println(texto);
		
	}
	
	//crear Json
	public void createJson() throws IOException {
		
		JSONObject json = new JSONObject();
		json.put("nombre", "");
		json.put("password", "");
		json.put("configurado", false);
		
		String textJson = json.toString();
		
		guardarArchivo(textJson);
		
		
		
		
	}
	
	
	

}
