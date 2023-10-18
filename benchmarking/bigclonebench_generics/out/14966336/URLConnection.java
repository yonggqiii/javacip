// added by JavaCIP
public interface URLConnection {

    public abstract void connect();

    public abstract InputStream getInputStream();

    public abstract boolean getContentLength();

    public abstract String getContentEncoding();
}
