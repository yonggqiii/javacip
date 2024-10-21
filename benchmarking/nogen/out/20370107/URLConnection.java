// added by JavaCIP
public interface URLConnection extends HttpURLConnection {

    public abstract void connect();

    public abstract void setRequestProperty(String arg0, Object arg1);

    public abstract InputStream getInputStream();

    public abstract void setUseCaches(boolean arg0);

    public abstract String getHeaderField(boolean arg0);
}
