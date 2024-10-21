// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setReadTimeout(boolean arg0);

    public abstract void setConnectTimeout(boolean arg0);

    public abstract int getResponseCode();

    public abstract long getLastModified();

    public abstract String getResponseMessage();
}
