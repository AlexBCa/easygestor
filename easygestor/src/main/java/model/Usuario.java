package model;
import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="Usuario")
public class Usuario implements Serializable{
	
	@Id
	@Column(name="Nsocio")
	private int Nsocio;
	private String dni;
	private String nombre;
	private String apellidos;
	private int telefono;
	private String email;
	private String direccion;
	
	public Usuario() {
		
	}
	
	
	
	public Usuario(int nsocio, String dni, String nombre, String apellidos, int telefono, String email,
			String direccion) {
		super();
		Nsocio = nsocio;
		this.dni = dni;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.telefono = telefono;
		this.email = email;
		this.direccion = direccion;
	}



	public int getNsocio() {
		return Nsocio;
	}
	public void setNsocio(int nsocio) {
		Nsocio = nsocio;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellido) {
		this.apellidos = apellido;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	
	

}
