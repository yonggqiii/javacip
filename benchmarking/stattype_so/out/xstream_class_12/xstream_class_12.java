public class xstream_class_12 implements Converter {

    private String alias;

    public xstream_class_12(String alias) {
        super();
        this.alias = alias;
    }

    public boolean canConvert(Class type) {
        return true;
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        List<String> list = (List<String>) source;
        for (String string : list) {
            writer.startNode(alias);
            writer.setValue(string);
            writer.endNode();
        }
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        throw new UnsupportedOperationException("xstream_class_12 does not offer suport for unmarshal operation");
    }
}
