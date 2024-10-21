public class xstream_class_28 implements Converter {
		public boolean canConvert(Class clazz) {
			return Map.class.isAssignableFrom(clazz);
		}

		public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
			Map<String, Integer> map = (Map<String, Integer>) value;
			for (Entry<String, Integer> entry : map.entrySet()) {
				writer.startNode(entry.getKey());
				writer.setValue(entry.getValue());
				writer.endNode();
			}
		}

		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
			Map<String, Integer> map = new HashMap<String, Integer>();

			while (reader.hasMoreChildren()) {
				reader.moveDown();
				map.put(reader.getNodeName(), new Integer(reader.getValue()));
				reader.moveUp();
			}
			return map;
		}
	}
