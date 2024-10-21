// added by JavaCIP
public interface HttpURLConnection {

    public static boolean HTTP_OK = false;

    public abstract void setRequestMethod(String arg0);

    public abstract boolean getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract void setDoInput(boolean arg0);

    public abstract boolean getOutputStream();
}
