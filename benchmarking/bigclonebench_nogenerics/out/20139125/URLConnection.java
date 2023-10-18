// added by JavaCIP
public interface URLConnection extends HttpURLConnection {

    public abstract void connect();

    public abstract String getHeaderFieldKey(int arg0);

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract void addRequestProperty(String arg0, String arg1);

    public abstract void setDoInput(boolean arg0);

    public abstract boolean getOutputStream();

    public abstract String getHeaderField(int arg0);
}
