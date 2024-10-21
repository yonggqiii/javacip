public class hibernate_class_46 {

	public static final ThreadLocal session = new ThreadLocal();
	private static hibernate_class_46 me;

	static {
		// try {
			me = new hibernate_class_46();
	}

	private hibernate_class_46() {
	}

	public Session currentSession() {
		Session s = (Session) session.get();
		if (s != null) {
			s.setCacheMode(CacheMode.REFRESH);
		}

		if (s == null) {
			s = openSession();

			session.set(s);
		}

		return s;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
	}

	public static Session openSession() {
		Session session = null;
		return session;
	}
}
