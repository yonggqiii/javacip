// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public abstract void disconnect();

    public abstract void setRequestProperty(String arg0, String arg1);

    public abstract int getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract void setAllowUserInteraction(boolean arg0);

    public abstract void setUseCaches(boolean arg0);

    public abstract void setDoInput(boolean arg0);
}
