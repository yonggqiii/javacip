// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setDoInput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract int getContentLength();

    public abstract void disconnect();
}
