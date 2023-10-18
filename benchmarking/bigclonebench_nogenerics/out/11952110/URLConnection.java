// added by JavaCIP
public interface URLConnection {

    public abstract void connect();

    public abstract void setReadTimeout(boolean arg0);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract void setConnectTimeout(boolean arg0);

    public abstract long getLastModified();
}
