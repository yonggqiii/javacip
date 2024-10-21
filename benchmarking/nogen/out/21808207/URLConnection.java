// added by JavaCIP
public interface URLConnection {

    public abstract int getContentLength();

    public abstract void connect();

    public abstract int getContentEncoding();

    public abstract int getRequestProperties();

    public abstract long getLastModified();

    public abstract void getContent();

    public abstract boolean getPermission();
}
