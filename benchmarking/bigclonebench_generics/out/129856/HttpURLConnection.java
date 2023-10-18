// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void connect();

    public abstract boolean getResponseCode();

    public abstract InputStream getInputStream();

    public abstract void addRequestProperty(String arg0, String arg1);

    public abstract void addRequestProperty(String arg0, boolean arg1);

    public abstract boolean getResponseMessage();

    public abstract void setDoInput(boolean arg0);
}
