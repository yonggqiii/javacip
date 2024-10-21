// added by JavaCIP
public interface HttpURLConnection {

    public abstract int getContentLength();

    public abstract void connect();

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract int getResponseCode();

    public abstract InputStream getInputStream();
}
