// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_MOVED_PERM = 0;

    public static void setFollowRedirects(boolean arg0) {
    }

    public abstract void setRequestMethod(String arg0);

    public abstract void setDoOutput(boolean arg0);

    public abstract int getResponseCode();
}
