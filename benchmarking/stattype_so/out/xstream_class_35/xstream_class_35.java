public class xstream_class_35 {

    public void testName() {
        XStream xStream = new XStream();
        xStream.registerConverter(new ToSingleValue());
        Mapper mapper = xStream.getMapper();
        ReflectionProvider reflectionProvider = xStream.getReflectionProvider();
        ConverterLookup converterLookup = xStream.getConverterLookup();
        String valueField = null;
        Converter converter = null;
        xStream.registerConverter(converter);
        System.out.println(xStream.toXML(new AClass()));
    }

    protected MapperWrapper wrapMapper(MapperWrapper next) {
        return next;
    }
}

class AClass {

    public String value = "This should be in an attribute";

    public CompositeClass anotherValue = new CompositeClass();
}

class CompositeClass {

    public Integer value = 42;
}

class ToSingleValue implements SingleValueConverter {

    public boolean canConvert(Class type) {
        return CompositeClass.class == type;
    }

    public String toString(Object obj) {
        CompositeClass cCompositeClass = (CompositeClass) obj;
        return cCompositeClass.value.toString();
    }

    public Object fromString(String str) {
        return new CompositeClass();
    }
}
