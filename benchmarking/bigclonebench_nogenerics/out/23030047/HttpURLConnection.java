// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract int getContentLength();

    public abstract void connect();

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract void setUseCaches(boolean arg0);

    public abstract void setDoInput(boolean arg0);
}
