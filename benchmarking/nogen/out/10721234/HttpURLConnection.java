// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract int getResponseCode();

    public abstract InputStream getInputStream();

    public abstract int getContentLength();

    public abstract void disconnect();
}
