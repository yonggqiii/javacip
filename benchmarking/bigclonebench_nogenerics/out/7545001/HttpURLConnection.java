// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void disconnect();

    public abstract void connect();

    public abstract void addRequestProperty(String arg0, boolean arg1);

    public abstract Object getContent();
}
