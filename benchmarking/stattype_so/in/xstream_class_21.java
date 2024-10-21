public class xstream_class_21 implements Converter {

		private final Map<String, String> attributes;

		private final Class<?> clazz;

		private final Mapper mapper;

		private final ReflectionProvider reflectionProvider;

		public xstream_class_21(Mapper mapper, ReflectionProvider reflectionProvider, Class<?> clazz,
				Map<String, String> attributes) {
			super();
			this.mapper = mapper;
			this.reflectionProvider = reflectionProvider;
			this.attributes = attributes;
			this.clazz = clazz;
		}

		@Override
		public boolean canConvert(Class cls) {
			return cls == clazz;
		}

		@Override
		public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
			for (String key : attributes.keySet()) {
				writer.addAttribute(key, attributes.get(key));
			}

			Converter converter = new ReflectionConverter(mapper, reflectionProvider);
			Object p = null;
			context.convertAnother(p, converter);
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader arg0, UnmarshallingContext arg1) {
			// TODO Auto-generated method stub
			return null;
		}

	}
