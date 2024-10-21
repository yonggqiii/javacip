public class xstream_class_22 {
	private String name;

	// any element named numberOfEmployees should go into this list
	protected List<NumberOfPersonnel> numberOfEmployees;

	public xstream_class_22(String name, List<NumberOfPersonnel> noEmp) {
		this.name = name;
		this.numberOfEmployees = noEmp;
	}

	public String toString() {
		return name + ", " + numberOfEmployees;
	}

	public static void main(String[] args) throws Exception {
		XStream xs = new XStream();
		xs.processAnnotations(xstream_class_22.class);

		System.out.println("Unmarshalling:");
		System.out.println(xs.fromXML(new File("in.xml")));

		System.out.println("Marshalling:");
		System.out.println(xs.toXML(new xstream_class_22("World",
				Arrays.asList(new NumberOfPersonnel(2001, 1000), new NumberOfPersonnel(2002, 500)))));
	}

}

class NumberOfPersonnel {
  public NumberOfPersonnel(int year, double value) {
    this.year = year;
    this.value = value;
  }

  private int year;

  private double value;

  public String toString() {
    return year + ": " + value;
  }
}
