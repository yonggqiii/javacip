// added by JavaCIP
public interface URLConnection {

    public abstract void connect();

    public abstract Object getContentEncoding();

    public abstract void setRequestProperty(String arg0, boolean arg1);

    public abstract InputStream getInputStream();

    public abstract boolean getContentType();
}
