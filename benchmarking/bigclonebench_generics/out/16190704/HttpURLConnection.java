// added by JavaCIP
public interface HttpURLConnection {

    public static boolean HTTP_OK = false;

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract boolean getResponseCode();

    public abstract boolean getResponseMessage();

    public abstract boolean getInputStream();
}
