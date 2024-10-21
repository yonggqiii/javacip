public class xstream_class_32 {

    public static XStream instanciateXstreamForHibernate() {
        XStream xstream1 = new A();
        xstream1.registerConverter(new HibernateProxyConverter());
        xstream1.registerConverter(new HibernatePersistentCollectionConverter(xstream1.getMapper()));
        xstream1.registerConverter(new HibernatePersistentMapConverter(xstream1.getMapper()));
        xstream1.registerConverter(new HibernatePersistentSortedMapConverter(xstream1.getMapper()));
        xstream1.registerConverter(new HibernatePersistentSortedSetConverter(xstream1.getMapper()));
        return xstream1;
    }
}

class A extends XStream {

    protected MapperWrapper wrapMapper(final MapperWrapper next) {
        return new HibernateMapper(next);
    }
}
