// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_OK = 0;

    public abstract void setRequestMethod(String arg0);

    public abstract void connect();

    public abstract int getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract void setDoInput(boolean arg0);
}
