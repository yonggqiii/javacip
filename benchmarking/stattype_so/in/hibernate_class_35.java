public class hibernate_class_35 {
	public static void main(String[] args) {
		BasicConfigurator.configure();
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			//	        Label label = new Label("vijay");
			Query query = session.createQuery("from vij");
			List list = query.list();
			for (Iterator it = list.iterator(); it.hasNext();) {
				//	            System.out.println(label.getId());          
			}
			transaction.commit();

		} catch (HibernateException e) {

			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}

	}
}
