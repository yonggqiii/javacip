public class xstream_class_40 implements Converter {

    public boolean canConvert(Class clazz) {
        return clazz.equals(Integer.class);
    }

    public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        String text = (String) reader.getValue();
        Integer number = Integer.parseInt(text.trim());
        return number;
    }
}
