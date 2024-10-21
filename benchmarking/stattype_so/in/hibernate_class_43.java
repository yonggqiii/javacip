public class hibernate_class_43 {
	private static Logger log = Logger.getLogger(hibernate_class_43.class);
	private static SessionFactory sessionFactory;
	private static String confFile = "hibernate-test.properties";
	private static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();

	private hibernate_class_43() {

	}

	public static void buildSessionFactory() {
		Configuration configuration = new Configuration();
		synchronized (hibernate_class_43.class) {
			if (sessionFactory == null) {
				// try {
					Properties properties = new Properties();
					properties.load(hibernate_class_43.class.getClassLoader().getResourceAsStream(confFile));
					configuration.setProperties(properties);
				// } catch (Exception e) {
				// 	log.fatal("cannot load the specified hibernate properties file: " + confFile);
				// 	throw new RuntimeException("cannot load the specified hibernate properties file : " + confFile, e);
				// }
				sessionFactory = configuration.configure().buildSessionFactory();
			}
		}
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			buildSessionFactory();
		}
		return sessionFactory;
	}

	public static Session getCurrentSession() {
		if (!getSessionFactory().getCurrentSession().isOpen())
			getSessionFactory().openSession();
		return getSessionFactory().getCurrentSession();
	}
}
