package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import model.Crud;
import model.Libro;
import model.Prestamo;
import model.Usuario;

/**
 * Esta clase realiza los test de las funciones de la clase Crud
 * @author Alex
 *
 */
class TestsCrud{
	
	@BeforeClass
	void testCreate() {
		Crud c = new Crud();
		c.setup();
		
		
		Usuario user = new Usuario();
		user.setNsocio(9999);
		user.setDni("000000d");
		user.setNombre("Test");
		user.setApellidos("Test");
		user.setDireccion("calle test");
		user.setEmail("test@falso.com");
		user.setTelefono(555555);
		
	    int id = c.create(user);
	    
	    Assertions.assertTrue(id > 0);
	     


	}
	
	@Test
	void testReadUser() {
		Crud c = new Crud();
		c.setup();
		
		Usuario us = c.readUsuario(9999);
		
		assertNotNull(us);
		
		
	}
	
	@Test
	void testUpdate() {
		Crud c = new Crud();
		SessionFactory s =c.setup();
		
		Usuario user = new Usuario();
		user.setNsocio(9999);
		user.setDni("000000d");
		user.setNombre("Test_actualizado");
		user.setApellidos("Test");
		user.setDireccion("calle test");
		user.setEmail("test@falso.com");
		user.setTelefono(555555);
		
		c.update(user);
		c.readUsuario(9999);
		Session session =  s.openSession();
		

		
		
		Usuario updatedUser = session.find(Usuario.class, 9999);
		
		assertEquals("Test_actualizado", updatedUser.getNombre());
		
		
	}
	
	@Test
	void testFindAllUsuarios() {
		Crud c = new Crud();
		SessionFactory s =c.setup();
		ArrayList<Usuario> usuarios = (ArrayList<Usuario>) c.findAllUsuarios();
		
		assertNotNull(usuarios);
	}
	
	@Test
	void testsearchAllUsuarios() {
		Crud c = new Crud();
		SessionFactory s =c.setup();
		ArrayList<Usuario> usuarios = (ArrayList<Usuario>) c.searchAllUsuarios("j");
		
		assertNotNull(usuarios);
		
		
	}
	
	@Test
	void testsearchAllLibros() {
		Crud c = new Crud();
		SessionFactory s =c.setup();
		ArrayList<Libro> libros = (ArrayList<Libro>) c.searchAllLibros("gen");
		assertNotNull(libros);
		
		
	}
	
	@Test
	void testsearchAllPrestamos() {
		Crud c = new Crud();
		SessionFactory s =c.setup();
		ArrayList<Prestamo> prestamos = (ArrayList<Prestamo>) c.searchAllPrestamos("254");
		assertNotNull(prestamos);
		
		
	}
	
	@AfterClass
	void testDelete() {
		Crud c = new Crud();
		Usuario u =c.readUsuario(9999);
		
		c.delete(u);
		
		Usuario borrado = c.readUsuario(9999);
		
		assertNull(borrado);
		
	}



}