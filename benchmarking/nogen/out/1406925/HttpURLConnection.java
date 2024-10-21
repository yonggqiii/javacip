// added by JavaCIP
public interface HttpURLConnection {

    public abstract void connect();

    public abstract InputStream getInputStream();

    public abstract int getResponseCode();

    public abstract String getHeaderField(String arg0);
}
