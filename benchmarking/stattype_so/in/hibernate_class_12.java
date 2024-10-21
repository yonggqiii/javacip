public class hibernate_class_12 {
	public void main() {
		Session session = null;
		// try {

			SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
			session = sessionFactory.openSession();

			String id = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
					.get("storeId");

			Transaction t = session.beginTransaction();
			t.commit();
		// } catch (Exception e) {
		// } finally {
			session.close();
		// }
	}
}
