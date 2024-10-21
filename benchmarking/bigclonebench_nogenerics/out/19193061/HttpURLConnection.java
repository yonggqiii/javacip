// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_OK = 0;

    public static int HTTP_MOVED_TEMP = 0;

    public static int HTTP_MOVED_PERM = 0;

    public abstract void disconnect();

    public static void setFollowRedirects(boolean arg0) {
    }

    public abstract void connect();

    public abstract int getResponseCode();

    public abstract boolean getHeaderField(String arg0);
}
