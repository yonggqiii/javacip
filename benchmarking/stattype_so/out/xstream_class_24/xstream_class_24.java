public class xstream_class_24 extends ReflectionConverter {

    public xstream_class_24(Mapper mapper, ReflectionProvider reflectionProvider) {
        super(mapper, reflectionProvider);
    }

    public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext context) {
        super.marshal(obj, writer, context);
        writer.startNode("node4");
        writer.setValue(Double.toString(20));
        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        return super.unmarshal(reader, context);
    }

    public boolean canConvert(Class clazz) {
        Object o = null;
        return clazz.equals(o);
    }
}
