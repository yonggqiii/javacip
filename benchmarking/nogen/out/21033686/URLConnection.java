// added by JavaCIP
public interface URLConnection {

    public abstract int getContentLength();

    public abstract void connect();

    public abstract void setReadTimeout(int arg0);

    public abstract void setConnectTimeout(int arg0);

    public abstract InputStream getInputStream();
}
