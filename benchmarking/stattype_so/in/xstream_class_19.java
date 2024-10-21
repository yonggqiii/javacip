public class xstream_class_19 implements Converter {

		public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
			// TODO: Get annotation value from object 'source' with name of tag via Reflection.
			// Or add a method to the AnimalConfig interface giving you tag name to put to serialization output.
		}

		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
			Class canConvert = null;
			// TODO: use reflection to create animal object based on what you xml tag you have at hahd.
			return context.convertAnother(context.currentObject(), canConvert);
		}

		public boolean canConvert(Class type) {
			return true;
		}
	}
