// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setInstanceFollowRedirects(boolean arg0);

    public abstract int getResponseCode();

    public abstract String getHeaderField(String arg0);

    public abstract void disconnect();
}
