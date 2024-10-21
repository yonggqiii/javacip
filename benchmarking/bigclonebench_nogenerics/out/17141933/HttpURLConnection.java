// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_UNAVAILABLE = 0;

    public abstract void setFollowRedirects(boolean arg0);

    public abstract void connect();

    public abstract String getContentEncoding();

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract int getResponseCode();

    public abstract InputStream getInputStream();

    public abstract String getContentType();

    public abstract int getHeaderFieldInt(String arg0, int arg1);

    public abstract void setUseCaches(boolean arg0);
}
