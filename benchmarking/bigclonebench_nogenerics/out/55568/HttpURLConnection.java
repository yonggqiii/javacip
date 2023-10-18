// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void connect();

    public abstract int getResponseCode();

    public abstract boolean getResponseMessage();

    public abstract int getContentLength();

    public abstract void disconnect();
}
