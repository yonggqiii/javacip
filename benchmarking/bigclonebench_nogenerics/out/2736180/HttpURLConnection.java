// added by JavaCIP
public interface HttpURLConnection {

    public abstract void setRequestMethod(String arg0);

    public static void setFollowRedirects(boolean arg0) {
    }

    public abstract void connect();

    public abstract void setReadTimeout(int arg0);

    public abstract void setDefaultUseCaches(boolean arg0);

    public abstract void setConnectTimeout(int arg0);

    public abstract int getResponseCode();

    public abstract void setDoOutput(boolean arg0);

    public abstract InputStream getInputStream();

    public abstract void setUseCaches(boolean arg0);

    public abstract int getContentLength();

    public abstract void disconnect();
}
