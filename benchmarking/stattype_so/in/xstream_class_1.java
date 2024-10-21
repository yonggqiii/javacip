public class xstream_class_1 implements Converter {
	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

		while (reader.hasMoreChildren()) {
			reader.moveDown();
			String a;
			if (reader.getNodeName().equals("a")) {
				a = reader.getValue();
			} else if (reader.getNodeName().equals("b")) {
				Object b = (Object) context.convertAnother(reader, Object.class);
			} else if (reader.getNodeName().equals("c")) {
				a = reader.getValue();
			}

		}
		return context;
	}

	@Override
	public boolean canConvert(Class arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void marshal(Object arg0, HierarchicalStreamWriter arg1, MarshallingContext arg2) {
		// TODO Auto-generated method stub

	}
}
