// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void connect();

    public abstract void setReadTimeout(int arg0);

    public abstract void setDoOutput(boolean arg0);

    public abstract boolean getInputStream();
}
