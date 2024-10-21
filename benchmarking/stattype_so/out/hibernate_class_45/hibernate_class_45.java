public class hibernate_class_45 {

    public static void main(String[] args) {
        SessionFactory factory = new AnnotationConfiguration().configure().buildSessionFactory();
        Session session = factory.openSession();
        session.beginTransaction();
        // Message m1 = new Message("Hibernated a  messages on " + new Date());
        session.save("aa");
        session.getTransaction().commit();
        session.close();
    }
}
