// added by JavaCIP
public interface HttpURLConnection {

    public static boolean HTTP_CREATED = false;

    public static boolean HTTP_NO_CONTENT = false;

    public abstract void setDoOutput(boolean arg0);

    public abstract void setRequestMethod(String arg0);

    public abstract OutputStream getOutputStream();

    public abstract boolean getResponseCode();
}
