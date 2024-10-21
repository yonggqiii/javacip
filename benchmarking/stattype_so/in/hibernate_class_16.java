/**
 * This class represents the data mapper for an employee to the
 * database.
 */

	public class hibernate_class_16 {
		Session hibernateSession;

		public void setHibernateSession(Session hibernateSession) {
			this.hibernateSession = hibernateSession;
		}

		public void sethibernateSession(Session hibernateSession) {
			this.hibernateSession = hibernateSession;
		}

		public String execute() {

			List<Event> events = hibernateSession.createQuery("from Event").list();

			for (Event theEvent : events) {
				// blah blah...
			}

			return "success";
		}
	}

