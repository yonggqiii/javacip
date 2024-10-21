public class hibernate_class_38 {

	public static void main(String... args) {
		SessionFactory sessionFact = new Configuration().configure().buildSessionFactory();
		Session ses = sessionFact.openSession();
		Transaction tx = ses.beginTransaction();
		String bean = "";
		ses.save(bean);
		tx.commit();
		ses.close();
	}
}
