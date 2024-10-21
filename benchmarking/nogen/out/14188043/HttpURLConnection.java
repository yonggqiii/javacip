// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setConnectTimeout(boolean arg0);

    public abstract int getResponseCode();

    public abstract boolean getInputStream();

    public abstract void setUseCaches(boolean arg0);

    public abstract void setInstanceFollowRedirects(boolean arg0);

    public abstract String getHeaderField(String arg0);
}
