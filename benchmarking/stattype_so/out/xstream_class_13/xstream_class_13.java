public class xstream_class_13 extends ClassAliasingMapper {

    public xstream_class_13(Mapper wrapped) {
        super(wrapped);
    }

    public Class realClass(String elementName) {
        return super.realClass(elementName);
    }

    public String serializedClass(Class type) {
        return super.serializedClass(type);
    }
}
