// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setInstanceFollowRedirects(boolean arg0);

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract void connect();

    public abstract boolean getInputStream();
}
