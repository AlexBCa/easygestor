package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Prestamo")
public class Prestamo implements Serializable{
	
	@Id
	@Column(name="id_prestamo")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_prestamo;
	private int Nsocio;
	private int isbn;
	
	public Prestamo() {
		
	}

	public Prestamo(int id_prestamo, int nsocio, int isbn) {
		super();
		this.id_prestamo = id_prestamo;
		Nsocio = nsocio;
		this.isbn = isbn;
	}

	public int getId_prestamo() {
		return id_prestamo;
	}

	public void setId_prestamo(int id_prestamo) {
		this.id_prestamo = id_prestamo;
	}

	public int getNsocio() {
		return Nsocio;
	}

	public void setNsocio(int nsocio) {
		Nsocio = nsocio;
	}

	public int getIsbn() {
		return isbn;
	}

	public void setIsbn(int isbn) {
		this.isbn = isbn;
	}
	
	

}
