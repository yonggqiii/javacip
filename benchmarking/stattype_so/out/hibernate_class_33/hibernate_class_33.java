public class hibernate_class_33 {

    private static hibernate_class_33 instance = null;

    private SessionFactory sessionFactory;

    public static hibernate_class_33 getInstance() {
        if (hibernate_class_33.instance == null) {
            hibernate_class_33.instance = new hibernate_class_33().init();
        }
        return hibernate_class_33.instance;
    }

    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public Session getSession() {
        return this.sessionFactory.openSession();
    }

    private hibernate_class_33 init() {
        Configuration cfg = new Configuration();
        // cfg.addClass(domain.UserClass.class);
        // cfg.addClass(domain.User.class);
        cfg.setProperties(System.getProperties());
        cfg.configure();
        SessionFactory sessions = cfg.buildSessionFactory();
        sessionFactory = cfg.configure().buildSessionFactory();
        return this;
    }
}
