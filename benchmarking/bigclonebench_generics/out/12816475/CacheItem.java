// added by JavaCIP
public interface CacheItem {

    public abstract boolean getTranslationCount();

    public abstract InputStream getContentAsStream();

    public abstract Iterable<ResponseHeader> getHeaders();

    public abstract boolean getLastModified();

    public abstract boolean getFailCount();
}
