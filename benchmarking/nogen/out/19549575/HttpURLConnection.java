// added by JavaCIP
public interface HttpURLConnection {

    public abstract boolean getErrorStream();

    public abstract void connect();

    public abstract void setRequestProperty(String arg0, boolean arg1);

    public abstract void setConnectTimeout(byte arg0);

    public abstract int getResponseCode();

    public abstract boolean getInputStream();

    public abstract void setReadTimeout(byte arg0);

    public abstract boolean getContentEncoding();
}
