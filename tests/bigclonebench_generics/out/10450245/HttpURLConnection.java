// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void disconnect();

    public static void setFollowRedirects(boolean arg0) {
    }

    public abstract void connect();

    public abstract int getResponseCode();

    public abstract Map<String, List<String>> getHeaderFields();

    public abstract String getHeaderField(String arg0);
}
