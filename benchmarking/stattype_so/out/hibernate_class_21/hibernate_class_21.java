public class hibernate_class_21 {

    public static void main(String[] args) {
        Session session = null;
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        // BusinessCard card = new BusinessCard();
        // card.setId(1);
        // card.setName("Ozgur");
        // card.setDescription("Ac?klama");
        // try {
        transaction.begin();
        // session.save();
        transaction.commit();
        // } catch (Exception e) {
        // e.printStackTrace();
        // } finally {
        session.close();
        // }
    }
}
