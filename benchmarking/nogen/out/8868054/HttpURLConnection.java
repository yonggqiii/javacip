// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public static void setFollowRedirects(boolean arg0) {
    }

    public abstract void connect();

    public abstract Object getURL();

    public abstract int getResponseCode();
}
