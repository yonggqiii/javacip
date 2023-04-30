// added by JavaCIP
public interface HttpURLConnection extends URL {

    public abstract void connect();

    public abstract int getResponseCode();

    public abstract void disconnect();
}
