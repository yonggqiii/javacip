	/**
	 * Entity implementation class for Entity: hibernate_class_2
	 * 
	 */
	public class hibernate_class_2 implements Serializable {

		private int id;
		private String name;
		private static final long serialVersionUID = 1L;
		private hibernate_class_2 mainhibernate_class_2;
		private List<hibernate_class_2> parts;

		public hibernate_class_2() {
			super();
		}

		public int getId() {
			return this.id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public hibernate_class_2 getMainhibernate_class_2() {
			return mainhibernate_class_2;
		}

		public void setMainhibernate_class_2(hibernate_class_2 mainhibernate_class_2) {
			this.mainhibernate_class_2 = mainhibernate_class_2;
		}

		public List<hibernate_class_2> gethibernate_class_2s() {
			return parts;
		}

		public void sethibernate_class_2s(List<hibernate_class_2> parts) {
			this.parts = parts;
		}

	}
