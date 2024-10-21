// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_OK = 0;

    public abstract void setRequestMethod(String arg0);

    public abstract boolean getContentLength();

    public abstract void setFollowRedirects(boolean arg0);

    public abstract boolean getLastModified();

    public abstract boolean getResponseMessage();

    public abstract int getResponseCode();

    public abstract boolean getDate();
}
