// added by JavaCIP
public interface URLConnection extends HttpURLConnection {

    public abstract void setReadTimeout(int arg0);

    public abstract void setConnectTimeout(int arg0);

    public abstract boolean getInputStream();
}
