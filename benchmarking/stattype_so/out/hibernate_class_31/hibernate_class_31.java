public class hibernate_class_31 {

    public static List<?> getData() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        List<?> result = null;
        // try {
        session.beginTransaction();
        Query query = session.createQuery("from Users");
        result = query.list();
        session.getTransaction().commit();
        query.setReadOnly(true);
        query.setMaxResults(50);
        session.flush();
        session.close();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        return result;
    }
}
