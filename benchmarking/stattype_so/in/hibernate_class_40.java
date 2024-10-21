public class hibernate_class_40 {

	public static void main(String[] args) {

		Session session = null;
		//String query = "select example.id, example.data from Example example";

		// try {

			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();

			System.out.println("selecting records");
			Query q = session.getNamedQuery("getRecordsSP");
			//      Query q =  session.createSQLQuery("call getRecords","",Example.class);

			System.out.print("Done");
			List l = q.list();

			for (Iterator it = l.iterator(); it.hasNext();) {
				Object row[] = (Object[]) it.next();
				System.out.println(row.length);
				//      System.out.println(row[0]);
				//      System.out.println("ID " + row[0]);
				//  System.out.println("Data "+ row[1]);
			}

		// } catch (Exception e) {
		// 	e.printStackTrace();
		// } finally {
			session.flush();
			session.close();

		// }
	}
}
