// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void connect();

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract void setRequestProperty(String arg0, Object arg1);

    public abstract void setDoOutput(boolean arg0);

    public abstract boolean getInputStream();

    public abstract void setAllowUserInteraction(boolean arg0);

    public abstract void setUseCaches(boolean arg0);

    public abstract void setDoInput(boolean arg0);

    public abstract boolean getOutputStream();

    public abstract boolean getHeaderField(String arg0);
}
