// added by JavaCIP
public interface HttpURLConnection {

    public abstract void connect();

    public abstract void setReadTimeout(int arg0);

    public abstract void setConnectTimeout(int arg0);

    public abstract int getResponseCode();

    public abstract void getInputStream();
}
