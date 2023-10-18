// added by JavaCIP
public interface URLConnection {

    public abstract boolean getContentLength();

    public abstract void connect();

    public abstract InputStream getInputStream();

    public abstract boolean getContentType();

    public abstract boolean getLastModified();
}
