public abstract class hibernate_class_29<T> {

    private Class<T> domainClass;

    private SessionFactory sf;

    public hibernate_class_29(Class<T> domainClass) {
        this.domainClass = domainClass;
    }

    public SessionFactory getSessionFactory() {
        return sf;
    }

    public void setSessionFactory(SessionFactory sf) {
        this.sf = sf;
    }

    public void delete(T object) {
        getSession().delete(object);
    }

    public void save(T object) {
        getSession().saveOrUpdate(object);
    }

    public int countAll() {
        Criteria criteria = getSession().createCriteria(domainClass);
        criteria.setProjection(Projections.rowCount());
        return (Integer) criteria.uniqueResult();
    }

    public Session getSession() {
        // presumes a current session, which we have through the
        // OpenSessionInViewFilter; doesn't work without that
        return sf.getCurrentSession();
    }
}
