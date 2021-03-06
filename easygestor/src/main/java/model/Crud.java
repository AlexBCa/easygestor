package model;

import model.Usuario;

import java.sql.Date;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

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
    public SessionFactory setup() {
    	HibernateUtil hu = new HibernateUtil();
    	sessionFactory = hu.getSessionFactory();
    	return sessionFactory;
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
    public int create(Object obj) throws Exception, PersistenceException{
    	int id = 0;
    	Session session = null;
    	
    		session = sessionFactory.openSession();
    		session.beginTransaction();
            
            id = (Integer) session.save(obj);
            
            session.getTransaction().commit();
            session.close();

    		
    	
    	return id;
    	
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
    public void update(Object obj) throws Exception, HibernateException, PersistenceException{
    	
    	Session session = null;
    	

    	session = sessionFactory.openSession();
        session.beginTransaction();
            
        session.update(obj);
            
        session.getTransaction().commit();
        session.close();
           
    		
    	
    	
    }
 
    /**
     * Borra un registro en la base de datos.
     * @param obj. El objecto que queremos borrar.
     */
    public void delete(Object obj) throws Exception, HibernateException, PersistenceException{
    	Session session = null;
    	

    	session = sessionFactory.openSession();
        session.beginTransaction();
        	 
        session.delete(obj);
        	 
        session.getTransaction().commit();

    	session.close();
    		
    	
    	
    	
    }

    /**
     * Devuelve todo los usuarios en la base de datos.
     * @return List<usurio> que contiene lo usuarios.
     */
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
    /**
     * Devuelve todo los libros en la base de datos.
     * @return Devuelve un List<Libro> que contiene lo libros.
     */
    
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
    /**
     * Obtine todo los prestamos de la base de datos.
     * @return Devuelve un List<Prestamo> que contiene lo prestamos.
     */
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
    
    /**
     * Esta funci??n busca los usuarios que tenga el apellido pasado como parametro en la base de datos.
     * @param apellido. Un String que contenga parte de los apellidos.
     * @return ArrayList<Usuario>. Objeto que contiene los objetos Usuarios. 
     */
    public List<Usuario> searchAllUsuarios(String apellido){
    	Session session = null;
    	List<Usuario>usuarios = null;
    	try {
    		session = sessionFactory.openSession();
        	Query<Usuario> qy = session.createQuery("SELECT a FROM Usuario a WHERE apellidos LIKE :palabra", Usuario.class);
        	qy.setParameter("palabra", "%" +apellido+ "%");
        	
        	usuarios = qy.getResultList();
        	

    	}
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	finally {
    		if(session != null) {
    			session.close();
    		}
    	}
    	
    	return usuarios;
    }
    
    
    /**
     * Esta funci??n busca los libros que tenga el titulo pasado como parametro en la base de datos.
     * @param apellido. Un String que contenga parte del titulo.
     * @return ArrayList<Libro>. Lista que contiene los objetos libro. 
     */
    public List<Libro> searchAllLibros(String titulo){
    	Session session = null;
    	List<Libro>libros = null;
    	try {
    		session = sessionFactory.openSession();
        	Query<Libro> qy = session.createQuery("SELECT a FROM Libro a WHERE titulo LIKE :palabra", Libro.class);
        	qy.setParameter("palabra", "%" +titulo+ "%");
        	
        	libros = qy.getResultList();
        	

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
    
    
    /**
     * Esta funci??n busca los libros que tenga el titulo pasado como parametro en la base de datos.
     * @param apellido. Un String que contenga parte del titulo.
     * @return ArrayList<Libro>. Lista que contiene los objetos libro. 
     */
    public List<Prestamo> searchAllPrestamos(String Nsocio){
    	Session session = null;
    	List<Prestamo>prestamos = null;
    	try {
    		session = sessionFactory.openSession();
        	//Query<Prestamo> qy = session.createQuery("SELECT a FROM Prestamo a WHERE Nsocio LIKE :palabra", Prestamo.class);
        	//.setParameter("palabra", "%" +Nsocio+ "%");
        	
        	//prestamos = qy.getResultList();
        	/*
        	
        	CriteriaBuilder builder = session.getCriteriaBuilder();

        	CriteriaQuery<Prestamo> cr = builder.createQuery( Prestamo.class );
        	Root<Prestamo> root = cr.from( Prestamo.class );
        	cr.select(root);
        	cr.where(builder.like(root.get("Nsocio"), "2"));
        	
        	prestamos = session.createQuery(cr).getResultList();
        	
        	*/
        	
        	Criteria cats = session.createCriteria(Prestamo.class);
        	cats.add(Restrictions.sqlRestriction("Nsocio LIKE '%"+Nsocio+"%'"));
        	prestamos = cats.list();
        	
        	
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
    
    /**
     * Devuelve el n??mero de libros prestados que tiene un usuario.
     * @param nsocio
     * @return
     */
    
    public long contarPrestamos(int nsocio) {
    	Session session = null;
    	

    	session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "SELECT count(E.id_prestamo) FROM Prestamo E WHERE E.Nsocio = :usuario_nsocio";
        Query qy = session.createQuery(hql);
        qy.setParameter("usuario_nsocio", nsocio);
        long count  = (long) qy.uniqueResult();
            
        session.getTransaction().commit();
        session.close();
        
        return count;
    	
    }
    
    /**
     * Consigue el libro prestado filtrando por ISBN.
     * @param isbn
     * @return
     */
    public Libro getLibroPrestado(int isbn) {
    	Session session = null;
    	Libro lb = null;
    	try {
    		session = sessionFactory.openSession();
            session.beginTransaction();
            //String hql = "FROM Libro E WHERE E.isbn = (select p.isbn from Prestamo as p) AND E.isbn = :libro_ibsn";
            String hql = "SELECT distinct  E FROM Libro E WHERE E.isbn = (select distinct p.isbn from Prestamo as p WHERE p.isbn =:libro_ibsn) ";
            Query qy = session.createQuery(hql);
            qy.setParameter("libro_ibsn", isbn);
            
            lb = (Libro) qy.getResultList().get(0);
          
           
                
            session.getTransaction().commit();
    	}catch(Exception e) {
    		//e.printStackTrace();
    		
    		
    	}

    	
        session.close();
        
        return lb;
    	
    	
    }
    
    /**
     * Devuleve una lista con los prestamos filtrados por titulos.
     * @param titulo
     * @return
     */
    public List<Prestamo> getPrestamoPorTitulos(String titulo) {
    	
    	
    	Session session = null;
    	List<Prestamo>prestamos = null;
    	
    	session = sessionFactory.openSession();
    	//String hql = "FROM Prestamo p JOIN Libro l on p.isbn=:l.isbn";
    	String hql = "SELECT p FROM Prestamo p, Libro l where p.isbn = l.isbn AND titulo LIKE :palabra ";
    	Query qy = session.createQuery(hql);
    	qy.setParameter("palabra", "%" +titulo+ "%");
    	
    	prestamos = qy.getResultList();
    	
    	return prestamos;
    }
    
    /**
     * Devuelve todo los prestamos pasados de fecha pero no multados todavia.
     * @return
     */
    
    public List<Prestamo> getPrestamosSinMultar() {
    	Session session = null;
    	List<Prestamo>prestamos = null;
    	
    	try {
    		session = sessionFactory.openSession();
    		long now = System.currentTimeMillis();
	        Date dateNow= new Date(now);
        	//prestamos = session.createQuery("SELECT a FROM Prestamo a").getResultList();
    		String hql = "FROM Prestamo p WHERE  p.fecha_limite_prestamo < current_date AND multado = 0";
        	Query qy = session.createQuery(hql);
        	//qy.setParameter("current_date", dateNow);
        	prestamos = qy.getResultList();
        	
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
    
    /**
     * Devuelve todo los prestamos que tienen una multa.
     * @return
     */
    
    public List<Prestamo> getPrestamosMultados() {
    	Session session = null;
    	List<Prestamo>prestamos = null;
    	
    	try {
    		session = sessionFactory.openSession();
    		long now = System.currentTimeMillis();
	        Date dateNow= new Date(now);
        	//prestamos = session.createQuery("SELECT a FROM Prestamo a").getResultList();
    		String hql = "FROM Prestamo p WHERE  p.multado = 1";
        	Query qy = session.createQuery(hql);
        	//qy.setParameter("current_date", dateNow);
        	prestamos = qy.getResultList();
        	
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
    
    /**
     * Devuelve el numero de multas que tiene un usuario.
     * @param nsocio
     * @return
     */
    public long comprobrarMultaUsuario(int nsocio) {
    	Session session = null;
    	

    	session = sessionFactory.openSession();
        session.beginTransaction();
        String hql = "SELECT count(E.id_prestamo) FROM Prestamo E WHERE E.Nsocio = :usuario_nsocio AND E.multado =1";
        Query qy = session.createQuery(hql);
        qy.setParameter("usuario_nsocio", nsocio);
        long count  = (long) qy.uniqueResult();
            
        session.getTransaction().commit();
        session.close();
        
        return count;
    	
    }
    
 

}
