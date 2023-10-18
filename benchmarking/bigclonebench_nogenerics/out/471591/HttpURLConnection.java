// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void connect();

    public abstract boolean getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract boolean getInputStream();

    public abstract void addRequestProperty(String arg0, String arg1);

    public abstract boolean getResponseMessage();
}
