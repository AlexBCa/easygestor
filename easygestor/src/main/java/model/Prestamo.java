package model;

import java.io.Serializable;
import java.sql.Date;

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
	private Date fecha_prestamo;
	private Date fecha_limite_prestamo;
	
	public Prestamo() {
		
	}

	

	public Prestamo(int id_prestamo, int nsocio, int isbn, Date fecha_prestamo, Date fecha_limite_prestamo) {
		super();
		this.id_prestamo = id_prestamo;
		Nsocio = nsocio;
		this.isbn = isbn;
		this.fecha_prestamo = fecha_prestamo;
		this.fecha_limite_prestamo = fecha_limite_prestamo;
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
	
	public Date getFecha_prestamo() {
		return fecha_prestamo;
	}



	public void setFecha_prestamo(Date fecha_prestamo) {
		this.fecha_prestamo = fecha_prestamo;
	}



	public Date getFecha_limite_prestamo() {
		return fecha_limite_prestamo;
	}



	public void setFecha_limite_prestamo(Date fecha_limite_prestamo) {
		this.fecha_limite_prestamo = fecha_limite_prestamo;
	}


}
