// added by JavaCIP
public interface Map<A, B> {

    public abstract Iterable<Entry<String, List<String>>> entrySet();

    public abstract void put(String arg0, boolean arg1);

    public abstract void put(boolean arg0, String arg1);
}
