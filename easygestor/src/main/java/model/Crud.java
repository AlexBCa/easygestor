package model;

import model.Usuario;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Esta clase gestiona toda las llamadas a la base de datos.
 * @author AddPortatil
 *
 */
public class Crud {
	
	protected SessionFactory sessionFactory;
	
	
	/**
	 * Inicia y carga una session con hiberante
	 */
    public void setup() {
    	HibernateUtil hu = new HibernateUtil();
    	sessionFactory = hu.getSessionFactory();
    }
    
    /**
     * Elimina una sesionFactory
     */
    public void exit() {
    	sessionFactory.close();
    }
 
    /**
     * Crea un registro en la base de datos.
     * @param obj. Se envia un objecto que representa una tabla.
     * @throws ConstraintViolationException
     */
    public void create(Object obj){
    	Session session = null;
    	
    	try {
    		session = sessionFactory.openSession();
    		session.beginTransaction();
            
            session.save(obj);
            
            session.getTransaction().commit();

    		
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	finally{
    		if(session != null) {
    			session.close();
    		}
    	}
    	
    }
 
    /**
     * Devuelve un objecto Usuario que coincida con la clave pasada.
     * @param N_socio. La clave que debe coincidir con la del registro. 
     * @return Devuelve un Objecto Usuario.
     */
    public Usuario readUsuario(int N_socio) {
    	Session session = null;
    	Usuario user = null;
    	
    	try {
    		session = sessionFactory.openSession();
    		user = (Usuario)session.get(Usuario.class, N_socio);
    		
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	finally{
    		if(session != null) {
    			session.close();
    		}
    	}

        return user;
    }
    
    /**
     * Devuelve un objecto Libro que coincida con la clave pasada.
     * @param key. Una clave que debe coincidir con la del registro. 
     * @return Devuelve un Objecto Libro.
     */
    public Libro readLibro(int key) {
    	
    	Session session = null;
    	Libro lb = null;
    	
    	try {
    		session = sessionFactory.openSession();
    		lb = (Libro)session.get(Libro.class, key);
    		
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	finally{
    		if(session != null) {
    			session.close();
    		}
    	}

        return lb;
    }
    
    /**
     * Devuelve un objecto Prestamo que coincida con la clave pasada.
     * @param key. Una clave que debe coincidir con la del registro. 
     * @return Devuelve un Objecto Prestamo.
     */
    
    public Prestamo readPrestamo(int key) {
    	Session session = null;
    	Prestamo pr = null;
    	
    	try {
    		session = sessionFactory.openSession();
        	pr = (Prestamo)session.get(Prestamo.class, key);
    		
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	finally{
    		if(session != null) {
    			session.close();
    		}
    	}

        return pr;
    }
 
    /**
     * Actualiza los datos del objecto pasado.
     * @param obj
     */
    public void update(Object obj) {
    	
    	Session session = null;
    	
    	try {
    		session = sessionFactory.openSession();
            session.beginTransaction();
            
            session.update(obj);
            
            session.getTransaction().commit();
    		
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    	finally{
    		if(session != null) {
    			session.close();
    		}
    	}
    	
    }
 
    public void delete(Object obj) {
    	Session session = null;
    	
    	try {
    		session = sessionFactory.openSession();
        	session.beginTransaction();
        	 
        	session.delete(obj);
        	 
        	session.getTransaction().commit();
    	}
    	catch(Exception e) {
    		if(session != null) {
    		}
    		e.printStackTrace();
    	}
    	finally{
    		if(session != null) {
    			session.close();
    		}
    	}
    	
    	
    }

    public List<Usuario> findAllUsuarios() {
    	Session session = null;
    	List<Usuario>users = null;
    	
    	try {
    		session = sessionFactory.openSession();
        	users = session.createQuery("SELECT a FROM Usuario a", Usuario.class).getResultList();
        	
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	finally {
    		if(session != null) {
    			session.close();
    		}
    	}
        return users;     
    }
    
    public List<Libro> findAllLibros() {
    	
    	Session session = null;
    	List<Libro>libros = null;
    	
    	try {
    		session = sessionFactory.openSession();
        	libros = session.createQuery("SELECT a FROM Libro a", Libro.class).getResultList();
        	
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	finally {
    		if(session != null) {
    			session.close();
    		}
    	}
        
        return libros;     
    }
    
    public List<Prestamo> findAllPrestamos() {
    	Session session = null;
    	List<Prestamo>prestamos = null;
    	
    	try {
    		session = sessionFactory.openSession();
        	prestamos = session.createQuery("SELECT a FROM Prestamo a", Prestamo.class).getResultList();
        	
    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	finally {
    		if(session != null) {
    			session.close();
    		}
    	}
        
        return prestamos;     
    }
    
    

}
