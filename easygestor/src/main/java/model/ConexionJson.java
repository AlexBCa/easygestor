package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
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
	public String leerArchivo() throws IOException {
		
		String textoJson = Files.readString(Path.of(System.getProperty("user.dir")+"\\config.json"));
		System.out.println(textoJson);
		return textoJson;
	}
	
	//crear Json
	public void createJson() throws IOException {
		
		JSONObject json = new JSONObject();
		json.put("nombre", "");
		json.put("password", "");
		json.put("configurado", false);
		
		//String textJson = json.toString();
		StringWriter out = new StringWriter();
	    json.write(out);
	    
		guardarArchivo(out.toString());
		
		
		
		
	}
	
	public void addInfoJson(String name, String password) throws IOException {
		
		String textoJson = leerArchivo();
		JSONObject json = new JSONObject(textoJson);
		
		json.put("nombre", name);
		json.put("password", password);
		json.put("configurado", true);
		
		guardarArchivo(json.toString());
		
		
	}
	
	public boolean checkConfig() throws IOException {
		String textoJson = leerArchivo();
		JSONObject json = new JSONObject(textoJson);
		
		return (boolean) json.get("configurado");
		
	}
	
	
	
	
	

}
