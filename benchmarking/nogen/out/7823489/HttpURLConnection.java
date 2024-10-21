// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void disconnect();

    public abstract void connect();

    public abstract void setDoOutput(boolean arg0);

    public abstract boolean getInputStream();

    public abstract void setUseCaches(boolean arg0);

    public abstract boolean getOutputStream();
}
