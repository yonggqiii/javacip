// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void disconnect();

    public abstract void connect();

    public abstract int getResponseCode();

    public abstract boolean getLastModified();

    public abstract void setUseCaches(boolean arg0);

    public abstract String getHeaderField(String arg0);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract URL getURL();
}
