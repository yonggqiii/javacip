public class hibernate_class_7 {

    private final SessionFactory sessionFactory;

    private Session session;

    public hibernate_class_7() {
        this.sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
    }

    public hibernate_class_7(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * this is the function that return a session.So i'm free to implements any type of session in here.
     */
    public Session requestSession() {
        // if (session != null || session.isOpen()) {
        // return session;
        // } else {
        session = sessionFactory.openSession();
        // }
        return session;
    }
}
