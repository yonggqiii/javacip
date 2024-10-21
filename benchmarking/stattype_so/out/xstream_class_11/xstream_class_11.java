public class xstream_class_11 {

    public List<String> tags = new ArrayList<String>();

    public List<String> notags = new ArrayList<String>();

    public xstream_class_11(String tag, String tag2) {
        tags.add(tag);
        tags.add(tag2);
        notags.add(tag);
        notags.add(tag2);
    }

    public static void main(String[] args) {
        xstream_class_11 test = new xstream_class_11("foo", "bar");
        XStream xstream = new XStream();
        ClassAliasingMapper mapper = new ClassAliasingMapper(xstream.getMapper());
        mapper.addClassAlias("tag", String.class);
        xstream.registerLocalConverter(xstream_class_11.class, "tags", new CollectionConverter(mapper));
        System.out.println(xstream.toXML(test));
    }
}
