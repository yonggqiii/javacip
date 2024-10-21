class xstream_class_27 implements Converter {

    XStream xStream;

    private xstream_class_27(XStream xStream) {
        this.xStream = xStream;
    }

    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        String className = WordUtils.uncapitalize(source.getClass().getSimpleName());
        xStream.aliasField(className, Content.class, "objectContent");
        xStream.marshal(source, writer);
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        // To change body of implemented methods use File | Settings | File Templates.
        return null;
    }

    public boolean canConvert(Class type) {
        // To change body of implemented methods use File | Settings | File Templates.
        return true;
    }
}
