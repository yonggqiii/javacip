// added by JavaCIP
public interface URLConnection {

    public abstract int getContentLength();

    public abstract void connect();

    public abstract void setReadTimeout(boolean arg0);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract InputStream getInputStream();

    public abstract String getContentType();
}
