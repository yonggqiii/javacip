public class hibernate_class_44 extends DispatchAction {

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		if (log.isDebugEnabled()) {
			log.debug("Entering Master add method");
		}

		return mapping.findForward("success1");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  {

		SessionFactory sessionFactory = null;
		Session session = null;
		System.out.println("in cargo Action");
			sessionFactory = (SessionFactory) servlet.getServletContext().getAttribute("aa");
			session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();

			System.out.println("in cargo Action");

			tx.commit();
		return mapping.findForward("success");

	}

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  {
		System.out.println("in cargo search Action");
		SessionFactory sessionFactory = (SessionFactory) servlet.getServletContext().getAttribute("aaa");
		HttpSession session1 = request.getSession();
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		System.out.println("in cargo search Action");
		String name = null;
		String status = null;
		String createby = null;

			String SQL_QUERY = "createby";
			Query query = session.createQuery(SQL_QUERY);
			query.setParameter("name", name);
			query.setParameter("status", status);
			query.setParameter("createby", createby);
			ArrayList al = new ArrayList();
			for (Iterator i = query.iterate(); i.hasNext();) {
			}
			System.out.println("Cargo list is:" + al.size());
			session1.setAttribute("clist", al);
		System.out.println("search Cargo list is success");

		return mapping.findForward("success");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		SessionFactory sessionFactory = null;
		Session session = null;
		if (log.isDebugEnabled()) {
			log.debug("Entering Master Edit method");
		}

			sessionFactory = (SessionFactory) servlet.getServletContext().getAttribute("aa");
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			String qstring = "from cargo c where c.id=:id";
			Query query = session.createQuery(qstring);
			ArrayList all = new ArrayList();

			System.out.println("Edit Cargo list " + all.size());

			Set purchaseArray = new HashSet();
			session.flush();

		// return a forward to edit forward
		System.out.println("Edit Cargo list is success");
		return mapping.findForward("succ");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

			SessionFactory sessionFactory = (SessionFactory) servlet.getServletContext().getAttribute("aaa");
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			tx.commit();

		System.out.println("Deleted success");
		return mapping.findForward("succes");
	}

}
