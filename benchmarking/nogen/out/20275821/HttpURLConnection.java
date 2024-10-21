// added by JavaCIP
public interface HttpURLConnection {

    public abstract int getResponseCode();

    public abstract boolean getLastModified();

    public abstract void setInstanceFollowRedirects(boolean arg0);

    public abstract String getHeaderField(String arg0);

    public abstract boolean getContentLength();

    public abstract void disconnect();
}
