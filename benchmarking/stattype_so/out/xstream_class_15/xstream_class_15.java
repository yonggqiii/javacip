public class xstream_class_15 {

    public static void main(String[] arg) {
        XStream xs = new XStream();
        xs.registerConverter(new A(xs.getMapper()), XStream.PRIORITY_VERY_HIGH);
        Object processInstance = null;
        String s = xs.toXML(processInstance);
    }
}

class A extends CollectionConverter {

    Mapper m;

    A(Mapper m) {
        this.m = m;
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        PersistentSet ps = null;
        super.marshal(new HashSet(ps), writer, context);
    }

    public boolean canConvert(Class type) {
        return type.isAssignableFrom(PersistentSet.class);
    }
}
