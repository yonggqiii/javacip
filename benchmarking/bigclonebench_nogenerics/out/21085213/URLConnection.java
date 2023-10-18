// added by JavaCIP
public interface URLConnection {

    public abstract void setConnectTimeout(int arg0);

    public abstract void setReadTimeout(int arg0);

    public abstract int getContentLength();

    public abstract InputStream getInputStream();
}
