// added by JavaCIP
public interface URLConnection {

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract void setRequestProperty(String arg0, Object arg1);

    public abstract void connect();

    public abstract String getContentEncoding();

    public abstract boolean getInputStream();
}
