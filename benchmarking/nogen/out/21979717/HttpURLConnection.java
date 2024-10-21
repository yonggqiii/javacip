// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void connect();

    public abstract void setRequestProperty(boolean arg0, String arg1);

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract OutputStream getOutputStream();
}
