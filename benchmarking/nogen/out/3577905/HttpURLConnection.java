// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract InputStream getErrorStream();

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract boolean getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract boolean getInputStream();

    public abstract OutputStream getOutputStream();
}
