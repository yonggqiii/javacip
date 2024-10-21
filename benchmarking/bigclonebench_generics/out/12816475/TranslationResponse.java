// added by JavaCIP
public interface TranslationResponse {

    public static Object ETAG = null;

    public abstract void setTranslationCount(boolean arg0);

    public abstract void setEndState(boolean arg0);

    public abstract void setLastModified(boolean arg0);

    public abstract void setFailCount(boolean arg0);

    public abstract void addHeaders(Set<ResponseHeader> arg0);

    public abstract OutputStream getOutputStream();
}
