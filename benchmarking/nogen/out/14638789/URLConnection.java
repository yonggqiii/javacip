// added by JavaCIP
public interface URLConnection extends HttpURLConnection {

    public abstract void connect();

    public abstract void setReadTimeout(boolean arg0);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract void setRequestProperty(String arg0, Object arg1);

    public abstract void setConnectTimeout(boolean arg0);

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract void setUseCaches(boolean arg0);

    public abstract boolean getOutputStream();
}
