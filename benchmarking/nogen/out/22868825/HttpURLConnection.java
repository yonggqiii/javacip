// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void connect();

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract int getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract UNKNOWN_2340 getHeaderFields();

    public abstract void setUseCaches(boolean arg0);

    public abstract void setDoInput(boolean arg0);

    public abstract boolean getOutputStream();
}
