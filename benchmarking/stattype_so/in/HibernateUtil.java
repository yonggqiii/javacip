public class HibernateUtil {
	private static final SessionFactory sessionFactory;

	static {
		// try {
			// Create the SessionFactory from hibernate.cfg.xml
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		// } catch (Throwable e) {
		// 	System.err.println("Initial sessionFactory creation failed.  " + e);
		// 	throw new ExceptionInInitializerError(e);
		// }
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
