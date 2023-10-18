// added by JavaCIP
public interface HttpURLConnection {

    public abstract void disconnect();

    public abstract void setReadTimeout(boolean arg0);

    public abstract void setRequestProperty(String arg0, boolean arg1);

    public abstract int getResponseCode();

    public abstract boolean getInputStream();

    public abstract boolean getHeaderFields();

    public abstract boolean getResponseMessage();

    public abstract boolean getReadTimeout();

    public abstract boolean getConnectTimeout();
}
