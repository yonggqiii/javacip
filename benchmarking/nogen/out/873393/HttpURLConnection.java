// added by JavaCIP
public interface HttpURLConnection {

    public abstract void connect();

    public abstract int getResponseCode();

    public abstract boolean getInputStream();

    public abstract boolean getContentLength();

    public abstract void disconnect();
}
