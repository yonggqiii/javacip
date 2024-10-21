public class hibernate_class_50 {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public List<Name> getAll() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Name> names = (List<Name>) session.createQuery("from Name").list();
        return names;
    }

    public void save(Name name) {
        Session session = this.sessionFactory.getCurrentSession();
        session.saveOrUpdate(name);
        session.flush();
    }
}
