// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void connect();

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract void setConnectTimeout(int arg0);

    public abstract void setDoOutput(boolean arg0);

    public abstract boolean getInputStream();

    public abstract void setReadTimeout(int arg0);

    public abstract String getContentEncoding();
}
