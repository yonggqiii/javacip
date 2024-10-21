// added by JavaCIP
public interface HttpURLConnection {

    public static int HTTP_OK = 0;

    public abstract void setRequestMethod(String arg0);

    public abstract InputStream getErrorStream();

    public abstract void setRequestProperty(String arg0, boolean arg1);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract int getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract boolean getResponseMessage();

    public abstract UNKNOWN_74 getOutputStream();
}
