// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setInstanceFollowRedirects(boolean arg0);

    public abstract void setAllowUserInteraction(boolean arg0);

    public abstract void connect();

    public abstract InputStream getInputStream();
}
