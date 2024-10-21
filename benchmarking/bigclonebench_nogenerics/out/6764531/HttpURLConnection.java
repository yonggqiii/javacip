// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_OK = 0;

    public abstract char getResponseCode();

    public abstract InputStream getInputStream();

    public abstract String getContentType();

    public abstract void setAllowUserInteraction(boolean arg0);

    public abstract boolean getResponseMessage();
}
