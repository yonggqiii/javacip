public class hibernate_class_18 {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        // try {
        // Create the SessionFactory from hibernate.cfg.xml
        Configuration configuration = new Configuration().configure();
        return configuration.buildSessionFactory();
        // } catch (Throwable ex) {
        // // Make sure you log the exception, as it might be swallowed
        // System.err.println("Initial SessionFactory creation failed." + ex);
        // throw new ExceptionInInitializerError(ex);
        // }
    }

    public static SessionFactory getSessionFactory1() {
        return sessionFactory;
    }

    private static final SessionFactory sessionFactory1 = buildSessionFactory();

    private static SessionFactory buildSessionFactory1() {
        // try {
        // Create the SessionFactory from hibernate.cfg.xml
        Configuration configuration = new Configuration().configure();
        return configuration.buildSessionFactory();
        // } catch (Throwable ex) {
        // // Make sure you log the exception, as it might be swallowed
        // System.err.println("Initial SessionFactory creation failed." + ex);
        // throw new ExceptionInInitializerError(ex);
        // }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
