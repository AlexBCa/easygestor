package security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Aes256 {
	
	private static final String SECRET_KEY = "LacalveSecretaEsEsta";
	private static final String SALT = "ssshhhhhhhhhhh!!!!";
	private IvParameterSpec generateIv = generateIv();

	
	public Aes256(){
		
	}
	
	
	/**
	 * Convierte la contraseña interna en la clave secreta para la AES2256
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public SecretKey getSecretKeyFromPassword() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException {
		//Nos sirve para crear una clave del algoritmo sha256
		
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		// Crea una clave secreta a traves de una key
		KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
		
		SecretKey secret = new SecretKeySpec(factory.generateSecret(spec)
		        .getEncoded(), "AES");
		
		return secret;
		
	}
	
	/**
	 * Valor semialeatorio neceario para rellenar el cifrado ya que utilizamos el modo CBC
	 * @return
	 */
	public IvParameterSpec generateIv() {
		byte[] iv = {0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1};
	    //new SecureRandom().nextBytes(iv);
	    return new IvParameterSpec(iv);
	}
	
	/**
	 * Cifra el texto pasado por el parametro en AES256
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeySpecException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String cifrar(String input) throws NoSuchAlgorithmException, NoSuchPaddingException, 
	InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException, 
	InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
		
		this.generateIv = generateIv();
		// creamos una instancia pasandole el algorimto a utlizar.
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		//configuramos una instancia de cifrado.
		cipher.init(Cipher.ENCRYPT_MODE, getSecretKeyFromPassword(), this.generateIv);
		
		byte[] cipherText = cipher.doFinal(input.getBytes());
		
		return Base64.getEncoder()
		        .encodeToString(cipherText);
	}
	
	/**
	 * Descifra el texto pasado por parametro cifrado anteriormente.
	 * @param textoCifrado
	 * @return
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 * @throws NoSuchPaddingException
	 */
	public String descrifrar(String textoCifrado) throws IllegalBlockSizeException, BadPaddingException, 
	InvalidKeyException, InvalidAlgorithmParameterException, UnsupportedEncodingException, 
	NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
		
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
	    cipher.init(Cipher.DECRYPT_MODE, getSecretKeyFromPassword(), this.generateIv);
	    
	    byte[] plainText = cipher.doFinal(Base64.getDecoder()
	        .decode(textoCifrado));
	    
	    return new String(plainText);
	}
	

}
