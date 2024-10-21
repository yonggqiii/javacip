// added by JavaCIP
public interface Compression {

    public abstract boolean getEnabled();

    public abstract UNKNOWN_1058 getMinResponseSize();

    public abstract String[] getMimeTypes();

    public abstract Iterable<String> getExcludedUserAgents();
}
