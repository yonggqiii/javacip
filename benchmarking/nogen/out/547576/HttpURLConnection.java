// added by JavaCIP
public interface HttpURLConnection {

    public abstract boolean getExpiration();

    public abstract void connect();

    public abstract boolean getContentEncoding();

    public abstract boolean getInputStream();

    public abstract boolean getContentType();

    public abstract boolean getLastModified();

    public abstract boolean getHeaderField(int arg0);

    public abstract boolean getContentLength();

    public abstract void disconnect();

    public abstract String getHeaderFieldKey(int arg0);

    public abstract boolean getDate();
}
