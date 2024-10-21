public class hibernate_class_37 {

    public static void test() {
        String c = "";
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.save(c);
        session.getTransaction().commit();
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        hibernate_class_37.test();
    }
}

class HibernateUtil {

    private static final SessionFactory sessionFactory;

    static {
        // try {
        sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        // } catch (Throwable ex) {
        // // Log exception!
        // throw new ExceptionInInitializerError(ex);
        // }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}
