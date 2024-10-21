// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_MOVED_TEMP = 0;

    public abstract void disconnect();

    public abstract void connect();

    public abstract int getResponseCode();

    public abstract void setInstanceFollowRedirects(boolean arg0);

    public abstract String getHeaderField(String arg0);
}
