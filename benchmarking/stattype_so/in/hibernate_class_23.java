public class hibernate_class_23 {
	private static final ThreadLocal<Session> threadLocal = new ThreadLocal<Session>();
	private static Configuration configuration = new AnnotationConfiguration();
	private static SessionFactory sessionFactory;
	static {
		// try {
			String configFile = "";
			configuration.configure(configFile);
			sessionFactory = configuration.buildSessionFactory();
		// } catch (Exception e) {
		// }
	}

	private hibernate_class_23() {
	}

	public static Session getSession() {
		Session session = (Session) threadLocal.get();
		if (session == null || !session.isOpen()) {
			if (sessionFactory == null) {
				//    rebuildSessionFactory();//This method basically does what the static init block does
			}
      if (sessionFactory != null)
        session = sessionFactory.openSession();
			threadLocal.set(session);
		}
		return session;
	}
}
