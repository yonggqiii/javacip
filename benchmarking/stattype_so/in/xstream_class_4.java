public class xstream_class_4 {

	public static void main(String[] args) {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		classes.add(Integer.class);
		classes.add(Long.class);
		classes.add(Double.class);
		classes.add(Float.class);

		ArrayList<Number> numbers = new ArrayList<Number>();
		numbers.add(new Integer(3));
		numbers.add(new Long(3));
		numbers.add(new Double(3));
		numbers.add(new Float(3));

		XStream xml = new XStream();
		xml.alias("inty", Integer.class);
		xml.aliasType("inty", Integer.class);
		xml.aliasPackage("jl", "java.lang");

		System.out.println(xml.toXML(classes));
		System.out.println(xml.toXML(numbers));
	}

}
