public class hibernate_class_47 {

    public static boolean registerEvent() {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Transaction t = null;
        // try {
        Session s = sf.openSession();
        // start a new transaction
        t = s.beginTransaction();
        s.persist("aa");
        // commit transaction
        t.commit();
        // } catch (Exception ex) {
        // System.err.println("Error -->" + ex.getMessage());
        if (t != null)
            // rollback transaction on exception
            t.rollback();
        return false;
        // }
    }
}
