// added by JavaCIP
public interface HttpURLConnection {

    public static boolean HTTP_OK = false;

    public abstract void setRequestMethod(String arg0);

    public abstract void setRequestProperty(boolean arg0, String arg1);

    public abstract void connect();

    public abstract boolean getResponseCode();
}
