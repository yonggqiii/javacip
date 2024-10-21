// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_OK = 0;

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract InputStream getInputStream();

    public abstract int getResponseCode();
}
