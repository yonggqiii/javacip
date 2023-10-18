// added by JavaCIP
public interface HttpURLConnection {

    public abstract void disconnect();

    public abstract void setReadTimeout(int arg0);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract void setConnectTimeout(int arg0);

    public abstract boolean getInputStream();
}
