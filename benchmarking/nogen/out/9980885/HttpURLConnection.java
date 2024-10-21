// added by JavaCIP
public interface HttpURLConnection {

    public abstract InputStream getInputStream();

    public abstract int getResponseCode();

    public abstract int getContentLength();

    public abstract void disconnect();
}
