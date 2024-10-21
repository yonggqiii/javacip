// added by JavaCIP
public interface URLConnection extends HttpURLConnection {

    public abstract void connect();

    public abstract int getContentLength();

    public abstract InputStream getInputStream();
}
