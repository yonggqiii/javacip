// added by JavaCIP
public interface HttpURLConnection {

    public static boolean HTTP_OK = false;

    public abstract void setRequestMethod(String arg0);

    public abstract void connect();

    public abstract void setRequestProperty(boolean arg0, String arg1);

    public abstract void setRequestProperty(boolean arg0, boolean arg1);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract boolean getResponseCode();

    public abstract boolean getResponseMessage();
}
