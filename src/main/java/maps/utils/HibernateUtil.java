package maps.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();
    private static SessionFactory buildSessionFactory() {
        try {        	
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySetting(Environment.DRIVER,"org.postgresql.Driver")
				.applySetting(Environment.URL,"jdbc:postgresql://localhost:5432/trasporti")
				.applySetting(Environment.USER,"postgres")
				.applySetting(Environment.PASS,"ai-user-password")
				
				.applySetting(Environment.POOL_SIZE,"10")
				.applySetting(Environment.DIALECT,"org.hibernate.spatial.dialect.postgis.PostgisDialect")
				.applySetting(Environment.HBM2DDL_AUTO,"validate")
				.applySetting(Environment.SHOW_SQL,"false")
				.applySetting(Environment.FORMAT_SQL,"false")
				.applySetting(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread")
				.build();
	
			Metadata metadata= new MetadataSources(serviceRegistry)
							.addAnnotatedClass(maps.hibernate.BusLineHibernateImpl.class)
							.addAnnotatedClass(maps.hibernate.BusLineStopHibernateImpl.class)
							.addAnnotatedClass(maps.hibernate.BusStopHibernateImpl.class)
							.getMetadataBuilder()
							.applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
							.build();

			return metadata.getSessionFactoryBuilder().build();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed. " + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex); // IMPORTANT
        }
    }
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
