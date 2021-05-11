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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONObject;

import security.Aes256;

public class ConexionJson {
	
	Aes256 aes256;
	
	public ConexionJson() {
		aes256 = new Aes256();
	}
	

	
	//guardar json en memoria.
	
	/**
	 * Guarda un archivo json en el disco.
	 * @param json
	 * @throws IOException
	 */
	public void guardarArchivo(String json) throws IOException {
		File archivo = new File("config.json");
		
		FileWriter writer = new FileWriter(archivo);
		writer.write(json); 
	    writer.flush();
	    writer.close();
		
	}
	
	/**
	 * Lee un fichero json en disco.
	 * @return
	 * @throws IOException
	 */
	public String leerArchivo() throws IOException {
		
		String textoJson = Files.readString(Path.of(System.getProperty("user.dir")+"\\config.json"));
		System.out.println(textoJson);
		return textoJson;
	}
	
	/**
	 * Se crea un json con los parametros basicos y se guarda en disco.
	 * @throws IOException
	 */
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
	
	/**
	 * Añade el nombre y password al Json, cambia la disponibilidad a True, cifra y guarda en el disco.
	 * @param name
	 * @param password
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws InvalidKeySpecException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public void addInfoJson(String name, String password) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
		
		
		String textoJson = leerArchivo();
		JSONObject json = new JSONObject(textoJson);
		
		String userPasswordEncrypt = aes256.cifrar(password);
		
		System.out.println(userPasswordEncrypt);
		
		json.put("nombre", name);
		json.put("password", userPasswordEncrypt);
		json.put("configurado", true);
		
		guardarArchivo(json.toString());
		
		
	}
	
	/**
	 * Comprueba si el json esta configurado.
	 * @return
	 * @throws IOException
	 */
	public boolean checkConfig() throws IOException {
		String textoJson = leerArchivo();
		JSONObject json = new JSONObject(textoJson);
		
		return (boolean) json.get("configurado");
		
	}
	
	/**
	 * Devuelve el usuario y la contraseña del json, luego descifra la contraseña.
	 * @return
	 * @throws IOException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 */
	public String[] getUserAndPassFromJson() throws IOException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
		
		String textoJson = leerArchivo();
		JSONObject json = new JSONObject(textoJson);
		
		String user = json.getString("nombre");
		String password = json.getString("password");
		
		
		String passwordPlainText = aes256.descrifrar(password);
		System.out.println(passwordPlainText);
		
		String[] contenedor = {user, passwordPlainText};
		
		return contenedor;
	}
	

	
	public void setFecha() throws IOException {
		
		String textoJson = leerArchivo();
		JSONObject json = new JSONObject(textoJson);
		
		long now = System.currentTimeMillis();
        Date dateNow= new Date(now);
		

		json.put("Fecha", dateNow);
		
		
		guardarArchivo(json.toString());
		
	}
	
	public Date getFecha() throws IOException, ParseException {
		
		String textoJson = leerArchivo();
		JSONObject json = new JSONObject(textoJson);
		
		String dateTodayText = json.getString("Fecha");
		
		
		Date dateToday = Date.valueOf(dateTodayText);
		
		return dateToday;
		
		
	}
	
	public boolean compareFechas() throws IOException, ParseException {
		
		boolean valido = false;
		
		long now = System.currentTimeMillis();
        Date dateNow= new Date(now);
        System.out.println(dateNow);
        
        Date dateFromJson = getFecha();
        
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        
        
        if(dateNow.after(dateFromJson) && !(fmt.format(dateNow).equals(fmt.format(dateFromJson)))) {
        	valido = true;
        }
        
        return valido;
	}
	
	
	
	

}
