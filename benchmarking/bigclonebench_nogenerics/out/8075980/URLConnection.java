// added by JavaCIP
public interface URLConnection {

    public abstract void setUseCaches(boolean arg0);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract InputStream getInputStream();

    public abstract int getContentLength();
}
