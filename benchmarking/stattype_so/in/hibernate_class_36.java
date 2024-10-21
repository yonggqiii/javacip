public class hibernate_class_36 {
	public static void main(String[] args) {
		Session session = null;
		SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

		//        MyClass str1 = null;
		Transaction tx = null;
		// try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			//            str1 = new MyClass("hello");
			//            session.save(str1);
			tx.commit();
		// } catch (Exception e) {
		// 	e.printStackTrace();
		// } finally {
			session.close();
		// }
	}
}
