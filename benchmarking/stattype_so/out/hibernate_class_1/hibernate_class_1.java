public class hibernate_class_1 implements UserDAO {

    private SessionFactory sessionFactory;

    public hibernate_class_1() {
        AnnotationConfiguration annotConf = new AnnotationConfiguration();
        annotConf.addAnnotatedClass(User.class);
        annotConf.configure();
        // The line below generates the exception!
        sessionFactory = annotConf.buildSessionFactory();
    }

    public void store(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.getTransaction();
        try {
            tx.begin();
            session.saveOrUpdate(user);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public User getUser(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public User getUserByFirstLastName(String arg0, String arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    public User getUserByUsername(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    public int saveUser(User arg0) {
        // TODO Auto-generated method stub
        return 0;
    }
}
