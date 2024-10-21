// added by JavaCIP
public interface HttpURLConnection {

    public static boolean HTTP_UNAUTHORIZED = false;

    public static boolean HTTP_CREATED = false;

    public abstract void setRequestMethod(String arg0);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract boolean getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract boolean getInputStream();

    public abstract boolean getResponseMessage();

    public abstract boolean getOutputStream();
}
