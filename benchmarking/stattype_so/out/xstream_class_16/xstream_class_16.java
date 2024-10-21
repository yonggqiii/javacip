public class xstream_class_16 {

    public static void main(String[] arg) {
        XStream xstream = new A();
    }
}

class A extends XStream {

    protected MapperWrapper wrapMapper(MapperWrapper next) {
        return new MapperWrapper(next) {

            public boolean shouldSerializeMember(Class definedIn, String fieldName) {
                if (definedIn == Object.class) {
                    return false;
                }
                return super.shouldSerializeMember(definedIn, fieldName);
            }
        };
    }
}
