// added by JavaCIP
public interface HttpURLConnection {

    public static void setFollowRedirects(boolean arg0) {
    }

    public abstract void connect();

    public abstract int getResponseCode();

    public abstract InputStream getInputStream();

    public abstract UNKNOWN_243 getHeaderFields();
}
