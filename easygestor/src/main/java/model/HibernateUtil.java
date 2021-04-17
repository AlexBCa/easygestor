package model;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
/**
 * Esta clase construirá el objeto SessionFactory con la configuración
 * que hemos establecido en el archivo cfg
 * @author Alex
 *
 */
public class HibernateUtil {
	
	 private static StandardServiceRegistry registry;
	    private static SessionFactory sessionFactory;
	    
	    /**
	     * Carga la configuración del cfg y genera un SessionFactory
	     * @return sessionFactory. Devuelve el objecto configurado.
	     */
	    public static SessionFactory getSessionFactory() {
	        if (sessionFactory == null) {
	        	final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
	        	        .configure() // Carga la configuración de hibernate.cfg.xml
	        	        .build();
	            try {
	            	sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();


	            } catch (Exception e) {
	                e.printStackTrace();
	                if (registry != null) {
	                    StandardServiceRegistryBuilder.destroy(registry);
	                }
	            }
	        }
	        return sessionFactory;
	    }
	    
	    /**
	     * Destruye el objecto con la configuración cargada.
	     */
	    public static void shutdown() {
	        if (registry != null) {
	            StandardServiceRegistryBuilder.destroy(registry);
	        }
	    }

}
